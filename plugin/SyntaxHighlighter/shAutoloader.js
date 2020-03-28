(function() {

var sh = SyntaxHighlighter;

/**
 * Provides functionality to dynamically load only the brushes that a needed to render the current page.
 *
 * There are two syntaxes that autoload understands. For example:
 *
 * SyntaxHighlighter.autoloader(
 *     [ 'applescript',          'Scripts/shBrushAppleScript.js' ],
 *     [ 'actionscript3', 'as3', 'Scripts/shBrushAS3.js' ]
 * );
 *
 * or a more easily comprehendable one:
 *
 * SyntaxHighlighter.autoloader(
 *     'applescript       Scripts/shBrushAppleScript.js',
 *     'actionscript3 as3 Scripts/shBrushAS3.js'
 * );
 */
sh.autoloader = function()
{
	var list = arguments,
		elements = sh.findElements(),
		brushes = {},
		scripts = {},
		all = SyntaxHighlighter.all,
		allCalled = false,
		allParams = null,
		i
		;

	SyntaxHighlighter.all = function(params)
	{
		allParams = params;
		allCalled = true;
	};

	function addBrush(aliases, url)
	{
		for (var i = 0; i < aliases.length; i++)
			brushes[aliases[i]] = url;
	};

	function getAliases(item)
	{
		return item.pop
			? item
			: item.split(/\s+/)
			;
	}

	// create table of aliases and script urls
	for (i = 0; i < list.length; i++)
	{
		var aliases = getAliases(list[i]),
			url = aliases.pop()
			;

		addBrush(aliases, url);
	}

	// dynamically add <script /> tags to the document body
	for (i = 0; i < elements.length; i++)
	{
		var url = brushes[elements[i].params.brush];

		if(url && scripts[url] === undefined)
		{
			if(elements[i].params['html-script'] === 'true')
			{
				if(scripts[brushes['xml']] === undefined) {
					loadScript(brushes['xml']);
					scripts[url] = false;
				}
			}

			scripts[url] = false;
			loadScript(url);
		}
	}

	function loadScript(url)
	{
		var script = document.createElement('script'),
			done = false
			;

		script.src = url;
		script.type = 'text/javascript';
		script.language = 'javascript';
		script.onload = script.onreadystatechange = function()
		{
			if (!done && (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete'))
			{
				done = true;
				scripts[url] = true;
				checkAll();

				// Handle memory leak in IE
				script.onload = script.onreadystatechange = null;
				script.parentNode.removeChild(script);
			}
		};

		// sync way of adding script tags to the page
		document.body.appendChild(script);
	};

	function checkAll()
	{
		for(var url in scripts)
			if (scripts[url] == false)
				return;

		if (allCalled){
			if(!SyntaxHighlighter.vars.discoveredBrushes){
				SyntaxHighlighter.vars.discoveredBrushes = {};
			}
			// 1、 通过全局变量 SyntaxHighlighter 访问 brushes 属性获取所有的 Brush 对象
			for(var Brush in SyntaxHighlighter.brushes){
				// console.info(Brush);
				// 2、 通过 Brush 对象，读取它所有的 aliases 
				// console.info(SyntaxHighlighter.brushes[Brush].aliases)
				var aliases = SyntaxHighlighter.brushes[Brush].aliases;
				// 3、 将所有的 aliases 与 Brush 绑定存入 SyntaxHighlighter.vars.discoveredBrushes 中
				aliases.forEach(function(val,index){
					// 4、 如果有则不再保存（考虑）
					if(!SyntaxHighlighter.vars.discoveredBrushes[val]) {
						SyntaxHighlighter.vars.discoveredBrushes[val] = Brush;
					}
				});
			}
			SyntaxHighlighter.highlight(allParams);
		}
	};
};

})();
