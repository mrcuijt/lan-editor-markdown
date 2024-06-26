
名称
    Set-AuthenticodeSignature
    
摘要
    为 Windows PowerShell 脚本或其他文件添加 Authenticode 签名。
    
    
语法
    Set-AuthenticodeSignature [-FilePath] <string[]> [-Certificate] <X509Certif
    icate2> [-Force] [-HashAlgorithm <string>] [-IncludeChain <string>] [-Times
    tampServer <string>] [-Confirm] [-WhatIf] [<CommonParameters>]
    
    
说明
    Set-AuthenticodeSignature cmdlet 可为支持目标接口包 (SIP) 的任何文件添加 Authenticode 签名。
    
    在 Windows PowerShell 脚本文件中，签名采取文本块的形式，该文本块指示脚本中所执行指令的结束位置。如果运行此 cmdlet 时文件中
    已有签名，该签名将被删除。
    

相关链接
    Online version: http://go.microsoft.com/fwlink/?LinkID=113391
    about_Signing 
    about_Execution_Policies 
    Get-AuthenticodeSignature 
    Get-PfxCertificate 
    Get-ExecutionPolicy 
    Set-ExecutionPolicy 

备注
    若要查看示例，请键入: "get-help Set-AuthenticodeSignature -examples".
    有关详细信息，请键入: "get-help Set-AuthenticodeSignature -detailed".
    若要获取技术信息，请键入: "get-help Set-AuthenticodeSignature -full".


名称
    Set-AuthenticodeSignature
    
摘要
    为 Windows PowerShell 脚本或其他文件添加 Authenticode 签名。
    
    -------------------------- 示例 1 --------------------------
    
    C:\PS>$cert=Get-ChildItem -Path cert:\CurrentUser\my -CodeSigningCert
    
    C:\PS>Set-AuthenticodeSignature -FilePath PsTestInternet2.ps1 -certificate $cert
    
    
    说明
    -----------
    这些命令从 Windows PowerShell 证书提供程序中检索代码签名证书，并使用该证书对 Windows PowerShell 脚本进行签名。
    
    第一个命令使用 Get-ChildItem cmdlet 和 Windows PowerShell 证书提供程序来获取证书存储中 Cert:\Curr
    entUser\My 子目录下的证书。（Cert: 驱动器是由证书提供程序公开的驱动器。）仅由证书提供程序支持的 CodeSigningCert 参数
    将检索的证书限制为具有代码签名颁发机构的证书。此命令将结果存储在 $cert 变量中。
    
    第二个命令使用 Set-AuthenticodeSignature cmdlet 对 PSTestInternet2.ps1 脚本进行签名。它使用 F
    ilePath 参数指定脚本名称，并使用 Certificate 参数指定证书存储在 $cert 变量中。
    
    
    
    
    -------------------------- 示例 2 --------------------------
    
    C:\PS>$cert = Get-PfxCertificate C:\Test\Mysign.pfx 
    
    C:\PS>Set-AuthenticodeSignature -Filepath ServerProps.ps1 -Cert $cert
    
    
    说明
    -----------
    这些命令使用 Get-PfxCertificate cmdlet 查找代码签名证书。然后，它们使用该证书对 Windows PowerShell 脚本
    进行签名。
    
    第一个命令使用 Get-PfxCertificate cmdlet 查找 C:\Test\MySign.pfx 证书，并将其存储在 $cert 变量中
    。
    
    第二个命令使用 Set-AuthenticodeSignature 对脚本进行签名。Set-AuthenticodeSignature 的 FileP
    ath 参数指定要对其进行签名的脚本文件的路径，Cert 参数将包含证书的 $cert 变量传递到 Set-AuthenticodeSignature
    。
    
    如果证书文件受密码保护，Windows PowerShell 将提示您输入密码。
    
    
    
    
    -------------------------- 示例 3 --------------------------
    
    C:\PS>Set-AuthenticodeSignature -filepath c:\scripts\Remodel.ps1 -certifica
    te $cert -IncludeChain All -TimeStampServer "http://timestamp.fabrikam.com/
    scripts/timstamper.dll"
    
    
    说明
    -----------
    此命令添加信任链中包括根证书颁发机构，并由第三方时间戳服务器签名的数字签名。
    
    此命令使用 FilePath 参数指定要对其进行签名的脚本，并使用 Certificate 参数指定保存在 $cert 变量中的证书。它使用 Incl
    udeChain 参数来包含信任链（包括根证书颁发机构）中的所有签名。此外，还使用 TimeStampServer 参数向签名中添加时间戳。这可防止证
    书过期时脚本运行失败。
    
    
    
    


名称
    Set-AuthenticodeSignature
    
摘要
    为 Windows PowerShell 脚本或其他文件添加 Authenticode 签名。
    
    
语法
    Set-AuthenticodeSignature [-FilePath] <string[]> [-Certificate] <X509Certif
    icate2> [-Force] [-HashAlgorithm <string>] [-IncludeChain <string>] [-Times
    tampServer <string>] [-Confirm] [-WhatIf] [<CommonParameters>]
    
    
说明
    Set-AuthenticodeSignature cmdlet 可为支持目标接口包 (SIP) 的任何文件添加 Authenticode 签名。
    
    在 Windows PowerShell 脚本文件中，签名采取文本块的形式，该文本块指示脚本中所执行指令的结束位置。如果运行此 cmdlet 时文件中
    已有签名，该签名将被删除。
    

参数
    -Certificate <X509Certificate2>
        指定将用于对脚本或文件进行签名的证书。输入存储表示证书的对象的变量，或输入获取证书的表达式。
        
        要查找证书，请在证书驱动器 (Cert:) 中使用 Get-PfxCertificate 或 Get-ChildItem cmdlet。如果证
        书无效或者没有代码签名颁发机构，该命令将失败。
        
    -FilePath <string[]>
        指定要对其进行签名的文件的路径。
        
    -Force [<SwitchParameter>]
        允许该 cmdlet 将签名追加到只读文件。即使使用 Force 参数，该 cmdlet 也无法覆盖安全限制。
        
    -HashAlgorithm <string>
        指定 Windows 用于计算文件的数字签名的哈希算法。默认值为 SHA1，这是 Windows 默认的哈希算法。
        
        使用其他哈希算法签名的文件可能在其他系统上无法识别。
        
    -IncludeChain <string>
        确定证书信任链中的哪些证书包括在数字签名中。默认值为“NotRoot”。
        
        有效值包括：
        
        -- Signer：仅包括签名者的证书。
        
        -- NotRoot：包括证书链中的所有证书（根证书颁发机构除外）。
        
        --All：包括证书链中的所有证书。
        
    -TimestampServer <string>
        使用指定的时间戳服务器向签名中添加时间戳。请以字符串形式键入时间戳服务器的 URL。
        
        时间戳表示向文件中添加证书的确切时间。时间戳可防止证书过期时脚本运行失败，因为用户和程序可以在签名时确认证书有效。
        
    -Confirm [<SwitchParameter>]
        在执行命令之前提示您进行确认。
        
    -WhatIf [<SwitchParameter>]
        描述如果执行该命令会发生什么情况（无需实际执行该命令）。
        
    <CommonParameters>
        此 cmdlet 支持通用参数: Verbose、Debug、
        ErrorAction、ErrorVariable、WarningAction、WarningVariable、
        OutBuffer 和 OutVariable。有关详细信息，请键入
        “get-help about_commonparameters”。
    
    -------------------------- 示例 1 --------------------------
    
    C:\PS>$cert=Get-ChildItem -Path cert:\CurrentUser\my -CodeSigningCert
    
    C:\PS>Set-AuthenticodeSignature -FilePath PsTestInternet2.ps1 -certificate 
    $cert
    
    说明
    -----------
    这些命令从 Windows PowerShell 证书提供程序中检索代码签名证书，并使用该证书对 Windows PowerShell 脚本进行签名。
    
    第一个命令使用 Get-ChildItem cmdlet 和 Windows PowerShell 证书提供程序来获取证书存储中 Cert:\Curr
    entUser\My 子目录下的证书。（Cert: 驱动器是由证书提供程序公开的驱动器。）仅由证书提供程序支持的 CodeSigningCert 参数
    将检索的证书限制为具有代码签名颁发机构的证书。此命令将结果存储在 $cert 变量中。
    
    第二个命令使用 Set-AuthenticodeSignature cmdlet 对 PSTestInternet2.ps1 脚本进行签名。它使用 F
    ilePath 参数指定脚本名称，并使用 Certificate 参数指定证书存储在 $cert 变量中。
    
    
    
    
    -------------------------- 示例 2 --------------------------
    
    C:\PS>$cert = Get-PfxCertificate C:\Test\Mysign.pfx 
    
    C:\PS>Set-AuthenticodeSignature -Filepath ServerProps.ps1 -Cert $cert
    
    说明
    -----------
    这些命令使用 Get-PfxCertificate cmdlet 查找代码签名证书。然后，它们使用该证书对 Windows PowerShell 脚本
    进行签名。
    
    第一个命令使用 Get-PfxCertificate cmdlet 查找 C:\Test\MySign.pfx 证书，并将其存储在 $cert 变量中
    。
    
    第二个命令使用 Set-AuthenticodeSignature 对脚本进行签名。Set-AuthenticodeSignature 的 FileP
    ath 参数指定要对其进行签名的脚本文件的路径，Cert 参数将包含证书的 $cert 变量传递到 Set-AuthenticodeSignature
    。
    
    如果证书文件受密码保护，Windows PowerShell 将提示您输入密码。
    
    
    
    
    -------------------------- 示例 3 --------------------------
    
    C:\PS>Set-AuthenticodeSignature -filepath c:\scripts\Remodel.ps1 -certifica
    te $cert -IncludeChain All -TimeStampServer "http://timestamp.fabrikam.com/
    scripts/timstamper.dll"
    
    说明
    -----------
    此命令添加信任链中包括根证书颁发机构，并由第三方时间戳服务器签名的数字签名。
    
    此命令使用 FilePath 参数指定要对其进行签名的脚本，并使用 Certificate 参数指定保存在 $cert 变量中的证书。它使用 Incl
    udeChain 参数来包含信任链（包括根证书颁发机构）中的所有签名。此外，还使用 TimeStampServer 参数向签名中添加时间戳。这可防止证
    书过期时脚本运行失败。
    
    
    
    
备注
    若要查看示例，请键入: "get-help Set-AuthenticodeSignature -examples".
    有关详细信息，请键入: "get-help Set-AuthenticodeSignature -detailed".
    若要获取技术信息，请键入: "get-help Set-AuthenticodeSignature -full".


名称
    Set-AuthenticodeSignature
    
摘要
    为 Windows PowerShell 脚本或其他文件添加 Authenticode 签名。
    
语法
    Set-AuthenticodeSignature [-FilePath] <string[]> [-Certificate] <X509Certif
    icate2> [-Force] [-HashAlgorithm <string>] [-IncludeChain <string>] [-Times
    tampServer <string>] [-Confirm] [-WhatIf] [<CommonParameters>]
    
    
说明
    Set-AuthenticodeSignature cmdlet 可为支持目标接口包 (SIP) 的任何文件添加 Authenticode 签名。
    
    在 Windows PowerShell 脚本文件中，签名采取文本块的形式，该文本块指示脚本中所执行指令的结束位置。如果运行此 cmdlet 时文件中
    已有签名，该签名将被删除。
    

参数
    -Certificate <X509Certificate2>
        指定将用于对脚本或文件进行签名的证书。输入存储表示证书的对象的变量，或输入获取证书的表达式。
        
        要查找证书，请在证书驱动器 (Cert:) 中使用 Get-PfxCertificate 或 Get-ChildItem cmdlet。如果证
        书无效或者没有代码签名颁发机构，该命令将失败。
        
        是否必需?                    True
        位置?                        2
        默认值                
        是否接受管道输入?            false
        是否接受通配符?              False
        
    -FilePath <string[]>
        指定要对其进行签名的文件的路径。
        
        是否必需?                    True
        位置?                        1
        默认值                
        是否接受管道输入?            true (ByValue, ByPropertyName)
        是否接受通配符?              False
        
    -Force [<SwitchParameter>]
        允许该 cmdlet 将签名追加到只读文件。即使使用 Force 参数，该 cmdlet 也无法覆盖安全限制。
        
        是否必需?                    False
        位置?                        named
        默认值                
        是否接受管道输入?            false
        是否接受通配符?              False
        
    -HashAlgorithm <string>
        指定 Windows 用于计算文件的数字签名的哈希算法。默认值为 SHA1，这是 Windows 默认的哈希算法。
        
        使用其他哈希算法签名的文件可能在其他系统上无法识别。
        
        是否必需?                    False
        位置?                        named
        默认值                SHA1
        是否接受管道输入?            false
        是否接受通配符?              False
        
    -IncludeChain <string>
        确定证书信任链中的哪些证书包括在数字签名中。默认值为“NotRoot”。
        
        有效值包括：
        
        -- Signer：仅包括签名者的证书。
        
        -- NotRoot：包括证书链中的所有证书（根证书颁发机构除外）。
        
        --All：包括证书链中的所有证书。
        
        是否必需?                    False
        位置?                        named
        默认值                
        是否接受管道输入?            false
        是否接受通配符?              False
        
    -TimestampServer <string>
        使用指定的时间戳服务器向签名中添加时间戳。请以字符串形式键入时间戳服务器的 URL。
        
        时间戳表示向文件中添加证书的确切时间。时间戳可防止证书过期时脚本运行失败，因为用户和程序可以在签名时确认证书有效。
        
        是否必需?                    False
        位置?                        named
        默认值                
        是否接受管道输入?            false
        是否接受通配符?              False
        
    -Confirm [<SwitchParameter>]
        在执行命令之前提示您进行确认。
        
        是否必需?                    False
        位置?                        named
        默认值                
        是否接受管道输入?            false
        是否接受通配符?              False
        
    -WhatIf [<SwitchParameter>]
        描述如果执行该命令会发生什么情况（无需实际执行该命令）。
        
        是否必需?                    False
        位置?                        named
        默认值                
        是否接受管道输入?            false
        是否接受通配符?              False
        
    <CommonParameters>
        此 cmdlet 支持通用参数: Verbose、Debug、
        ErrorAction、ErrorVariable、WarningAction、WarningVariable、
        OutBuffer 和 OutVariable。有关详细信息，请键入
        “get-help about_commonparameters”。
    
输入
    System.String
        可以通过管道将包含文件路径的字符串传递给 Set-AuthenticodeSignature。
    
    
输出
    System.Management.Automation.Signature
        
    
    
注释
    
    
        
        
    
    -------------------------- 示例 1 --------------------------
    
    C:\PS>$cert=Get-ChildItem -Path cert:\CurrentUser\my -CodeSigningCert
    
    C:\PS>Set-AuthenticodeSignature -FilePath PsTestInternet2.ps1 -certificate 
    $cert
    
    
    说明
    -----------
    这些命令从 Windows PowerShell 证书提供程序中检索代码签名证书，并使用该证书对 Windows PowerShell 脚本进行签名。
    
    第一个命令使用 Get-ChildItem cmdlet 和 Windows PowerShell 证书提供程序来获取证书存储中 Cert:\Curr
    entUser\My 子目录下的证书。（Cert: 驱动器是由证书提供程序公开的驱动器。）仅由证书提供程序支持的 CodeSigningCert 参数
    将检索的证书限制为具有代码签名颁发机构的证书。此命令将结果存储在 $cert 变量中。
    
    第二个命令使用 Set-AuthenticodeSignature cmdlet 对 PSTestInternet2.ps1 脚本进行签名。它使用 F
    ilePath 参数指定脚本名称，并使用 Certificate 参数指定证书存储在 $cert 变量中。
    
    
    
    
    
    -------------------------- 示例 2 --------------------------
    
    C:\PS>$cert = Get-PfxCertificate C:\Test\Mysign.pfx 
    
    C:\PS>Set-AuthenticodeSignature -Filepath ServerProps.ps1 -Cert $cert
    
    
    说明
    -----------
    这些命令使用 Get-PfxCertificate cmdlet 查找代码签名证书。然后，它们使用该证书对 Windows PowerShell 脚本
    进行签名。
    
    第一个命令使用 Get-PfxCertificate cmdlet 查找 C:\Test\MySign.pfx 证书，并将其存储在 $cert 变量中
    。
    
    第二个命令使用 Set-AuthenticodeSignature 对脚本进行签名。Set-AuthenticodeSignature 的 FileP
    ath 参数指定要对其进行签名的脚本文件的路径，Cert 参数将包含证书的 $cert 变量传递到 Set-AuthenticodeSignature
    。
    
    如果证书文件受密码保护，Windows PowerShell 将提示您输入密码。
    
    
    
    
    
    -------------------------- 示例 3 --------------------------
    
    C:\PS>Set-AuthenticodeSignature -filepath c:\scripts\Remodel.ps1 -certifica
    te $cert -IncludeChain All -TimeStampServer "http://timestamp.fabrikam.com/
    scripts/timstamper.dll"
    
    
    说明
    -----------
    此命令添加信任链中包括根证书颁发机构，并由第三方时间戳服务器签名的数字签名。
    
    此命令使用 FilePath 参数指定要对其进行签名的脚本，并使用 Certificate 参数指定保存在 $cert 变量中的证书。它使用 Incl
    udeChain 参数来包含信任链（包括根证书颁发机构）中的所有签名。此外，还使用 TimeStampServer 参数向签名中添加时间戳。这可防止证
    书过期时脚本运行失败。
    
    
    
    
    
    
相关链接
    Online version: http://go.microsoft.com/fwlink/?LinkID=113391
    about_Signing 
    about_Execution_Policies 
    Get-AuthenticodeSignature 
    Get-PfxCertificate 
    Get-ExecutionPolicy 
    Set-ExecutionPolicy 

