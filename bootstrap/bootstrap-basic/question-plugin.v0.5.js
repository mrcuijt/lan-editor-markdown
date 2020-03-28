      function question(option){
        var settings = option;
        var questions = (option.questions) ? option.questions : []
        var currentQuestionIndex = 0;
        var template = {
            questionTemplate: '<h3>#title</h3>' + '<p>#content</p>',
            formTemplate: '<form>#</form>',
            topicTemplate: '<input name="id" type="text" class="hidden" value="#"/>',
            optionsTemplate: '<div class="form-group list-group">' +
                               '<label for="#answer" class="btn btn-default list-group-item">' + 
                                  '<span class="pull-left">#selectOption</span>' +
                                  '<input id="#answer" class="" type="#type" name="answer" attr-checked value="answerId"/>' +
                                  '<div class="clearfix"></div>' +
                               '</label>' +
                             '</div>',
            switchButtons: ['<div class="form-group row">' +
                             '<div class="col-xs-5 col-xs-offset-1">' +
                               '<button type="button" class="btn btn-default form-control previous">上一题</button>' +
                             '</div>' +
                             '<div class="col-xs-5">' +
                               '<button type="button" class="btn btn-default form-control next">下一题</button>' +
                             '</div>' +
                           '</div>',
                           '<div class="form-group row">' +
                             '<div class="col-xs-10 col-xs-offset-1">' +
                               '<button type="button" class="btn btn-default form-control previous">上一题</button>' +
                             '</div>' +
                           '</div>',
                           '<div class="form-group row">' +
                             '<div class="col-xs-10 col-xs-offset-1">' +
                               '<button type="button" class="btn btn-default form-control next">下一题</button>' +
                             '</div>' +
                           '</div>',
                           '<div class="form-group row">' +
                             '<div class="col-xs-5 col-xs-offset-1">' +
                               '<button type="button" class="btn btn-default form-control previous">上一题</button>' +
                             '</div>' +
                             '<div class="col-xs-5">' +
                               '<button type="button" class="btn btn-default form-control">提交</button>' +
                             '</div>' +
                           '</div>'],
            optionIds: ['answer-a', 'answer-b', 'answer-c', 'answer-d', 'answer-e', 'answer-f'],
            types: ['checkbox', 'radio']
          };
        function renderDom(){
          // 根据下标读取 questions 中的题目，生产答题信息
          var temp = renderTitle() + renderForm();
          $(".question").addClass("panel panel-default");
          $(".question").html(temp);
          var labels = $("label");
          labels.each(function(index, item){
            //alert(item.outerHTML);
            $(item).bind("click", chooseSelect);
          });
          checkState();
          $(".next").bind("click", next);
          $(".previous").bind("click", previous);
        }
        function renderTitle(){
          var temp = "";
          try {
            temp = template.questionTemplate.replace('#title', currentQuestion().title);
            temp = temp.replace('#content', currentQuestion().question);
          } catch(e){
            alert(e);
          }
          return temp;
        }
        function renderForm(){
          var temp = renderOptions();
          temp += renderTopic();
          temp += renderButtons();
          temp = template.formTemplate.replace('#', temp);
          return temp;
        }
        function renderTopic(){
          return template.topicTemplate.replace('#', currentQuestion().id);
        }
        function renderOptions(){
          var question = "";
          currentQuestion().selectOptions.forEach(function(item, index){
            var temp = template.optionsTemplate.replace(/#answer/g, template.optionIds[index]);
            temp = temp.replace('#selectOption', currentQuestion().selectOptions[index].option);
            temp = temp.replace('answerId', currentQuestion().selectOptions[index].id);
            temp = temp.replace('#type', template.types[currentQuestion().type]);
            // currentQuestion().selectOptions[index].id in currentQuestion().selectAnswer[]
            for(var i = 0; i < currentQuestion().selectAnswer.length; i++){
              if(currentQuestion().selectOptions[index].id == currentQuestion().selectAnswer[i]){
                temp = temp.replace('attr-checked', 'checked');
                break;
              }
            }
            question += temp;
          });
          return question;
        }
        function renderButtons(){
          var buttons = "";
          //alert(currentQuestionIndex)
          if(currentQuestionIndex == 0){
            // next
            buttons = template.switchButtons[2];
          } else if(currentQuestionIndex == questions.length - 1){
            buttons = template.switchButtons[3];
          } else if(currentQuestionIndex > 0 && currentQuestionIndex < questions.length){
            buttons = template.switchButtons[0];
            // previous / next
          } else {
            // previous
            buttons = template.switchButtons[1];
          }
          return buttons;
        }
        function currentQuestion(){
          return questions[currentQuestionIndex];
        }
        /** 选择事件*/
        function chooseSelect(e){
          if(currentQuestion().type == 0){
            chooseCheckbox(e);
          } else {
            chooseRadio(e);
          }
        }
        function chooseCheckbox(e){
          var tagName = $(e.target).get(0).tagName;
          var $label = (tagName === "LABEL") ? $(e.target) : $(e.target).parent("label");
          var checkbox = $label.find("input")[0].checked;
          if(checkbox == true){
            $label.addClass("list-group-item-success");
            $label.addClass("btn-success");
            $label.removeClass("btn-default");
            $label.find("input").attr("checked", "checked");
          } else {
            $label.addClass("btn-default");
            $label.removeClass("list-group-item-success");
            $label.removeClass("btn-success");
            $label.find("input").attr("checked", false);
          }
        }
        function chooseRadio(e){
          var tagName = $(e.target).get(0).tagName;
          var $label = (tagName === "LABEL") ? $(e.target) : $(e.target).parent("label");
          var checkbox = $label.find("input")[0].checked;
          if(checkbox == true){
            $("form label").each(function(index, item){
              $(item).addClass("btn-default");
              $(item).removeClass("list-group-item-success");
              $(item).removeClass("btn-success");
            })
            $label.addClass("list-group-item-success");
            $label.addClass("btn-success");
            $label.removeClass("btn-default");
            $label.find("input").attr("checked", "checked");
          } else {
            $label.addClass("btn-default");
            $label.removeClass("list-group-item-success");
            $label.removeClass("btn-success");
            $label.find("input").attr("checked", false);
          }
        }
        function checkState(){
          $("input").each(function(index, item){
            if(item.checked){
              $(item).parent("label").addClass("list-group-item-success");
            } else {
              $(item).parent("label").removeClass("list-group-item-success");
            }
          });
        }
        // 记录本题的答案选择
        function recored(){
          var formdata = $("form").serialize();
          formdata = formdata.replace(/answer=/g, '');
          currentQuestion().selectAnswer = formdata.split('&');
        }
        function form(){
          try {
            var params = $("<form/>").append($("form").html()).serialize();
            //var params = $("form").serialize();
              alert(params);
            //$.post("", params, function(data){
              //alert(data);
            //});
          } catch (e) {
            alert(e);
          }
        }
        function next(){
          form();
          recored();
          currentQuestionIndex++;
          renderDom();
        }
        function previous(){
          recored();
          currentQuestionIndex--;
          renderDom();
        }
        renderDom();
      }