######################################## 
# Send-TcpRequest.ps1 
## Send a TCP request to a remote computer, and return the response. 
## If you do not supply input to this script (via either the pipeline, or the 
## -InputObject parameter,) the script operates in interactive mode. 
## 
## Example: 
## 
## $http = @" 
## GET / HTTP/1.1 
## Host:cn.bing.com  
## `n`n 
## "@ 
## 
## $http | .\Send-TcpRequest cn.bing.com  80 
######################################## 
param( 
        [string] $remoteHost = "localhost", 
        [int] $port = 80, 
        [switch] $UseSSL, 
        [string] $inputObject, 
        [int] $commandDelay = 100 
     ) 

[string] $output = "" 

## Store the input into an array that we can scan over. If there was no input, 
## then we will be in interactive mode. 
$currentInput = $inputObject 
if(-not $currentInput) 
{ 
    $SCRIPT:currentInput = @($input) 
} 
$scriptedMode = [bool] $currentInput 

function Main 
{ 
    ## Open the socket, and connect to the computer on the specified port 
    if(-not $scriptedMode) 
    { 
        write-host "Connecting to $remoteHost on port $port" 
    } 

    trap { Write-Error "Could not connect to remote computer: $_"; exit } 
    $socket = new-object System.Net.Sockets.TcpClient($remoteHost, $port) 

    if(-not $scriptedMode) 
    { 
        write-host "Connected. Press ^D followed by [ENTER] to exit.`n" 
    } 

    $stream = $socket.GetStream() 

    if($UseSSL) 
    { 
        $sslStream = New-Object System.Net.Security.SslStream $stream,$false 
        $sslStream.AuthenticateAsClient($remoteHost) 
        $stream = $sslStream 
    } 

    $writer = new-object System.IO.StreamWriter $stream 

    while($true) 
    { 
        ## Receive the output that has buffered so far 
        $SCRIPT:output += GetOutput 

        ## If we're in scripted mode, send the commands, 
        ## receive the output, and exit. 
        if($scriptedMode) 
        { 
            foreach($line in $currentInput) 
            { 
                $writer.WriteLine($line) 
                $writer.Flush() 
                Start-Sleep -m $commandDelay 
                $SCRIPT:output += GetOutput 
            } 

            break 
        } 
        ## If we're in interactive mode, write the buffered 
        ## output, and respond to input. 
        else 
        { 
            if($output)  
            { 
                foreach($line in $output.Split("`n")) 
                { 
                    write-host $line 
                } 
                $SCRIPT:output = "" 
            } 

            ## Read the user's command, quitting if they hit ^D 
            $command = read-host 
            if($command -eq ([char] 4)) { break; } 

            ## Otherwise, Write their command to the remote host 
            $writer.WriteLine($command) 
            $writer.Flush() 
        } 
    } 

    ## Close the streams 
    $writer.Close() 
    $stream.Close() 

    ## If we're in scripted mode, return the output 
    if($scriptedMode) 
    { 
        $output 
    } 
} 

## Read output from a remote host 
function GetOutput 
{ 
    ## Create a buffer to receive the response 
    $buffer = new-object System.Byte[] 1024 
    $encoding = new-object System.Text.AsciiEncoding 

    $outputBuffer = "" 
    $foundMore = $false 

    ## Read all the data available from the stream, writing it to the 
    ## output buffer when done. 
    do 
    { 
        ## Allow data to buffer for a bit 
        start-sleep -m 1000 

        ## Read what data is available 
        $foundmore = $false 
        $stream.ReadTimeout = 1000 

        do 
        { 
            try 
            { 
                $read = $stream.Read($buffer, 0, 1024) 

                if($read -gt 0) 
                { 
                    $foundmore = $true 
                    $outputBuffer += ($encoding.GetString($buffer, 0, $read)) 
                } 
            } catch { $foundMore = $false; $read = 0 } 
        } while($read -gt 0) 
    } while($foundmore) 

    $outputBuffer 
} 
. Main
# SIG # Begin signature block
# MIIFuQYJKoZIhvcNAQcCoIIFqjCCBaYCAQExCzAJBgUrDgMCGgUAMGkGCisGAQQB
# gjcCAQSgWzBZMDQGCisGAQQBgjcCAR4wJgIDAQAABBAfzDtgWUsITrck0sYpfvNR
# AgEAAgEAAgEAAgEAAgEAMCEwCQYFKw4DAhoFAAQU1/LlJ7zmpRn6JnUEw0y3+4xX
# OF6gggNCMIIDPjCCAiqgAwIBAgIQl0kN+klO3Z5Ll5dr4O3NeDAJBgUrDgMCHQUA
# MCwxKjAoBgNVBAMTIVBvd2VyU2hlbGwgTG9jYWwgQ2VydGlmaWNhdGUgUm9vdDAe
# Fw0xOTA1MDcxNzQ0NDZaFw0zOTEyMzEyMzU5NTlaMBoxGDAWBgNVBAMTD1Bvd2Vy
# U2hlbGwgVXNlcjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALy2CA+P
# JYHodmGcWH1roIMMpZMeM9WAtFsGHRHPceeqcwtWGbobrvUoUtITuEswIiUnX5/g
# o9rq00dapmV/zhBt5TWKU/tdrAPEtpsIOztPgAWORTdOhi0sKZ92nyNSIAySQL1D
# YhtYtQ+QowL20koQc76IRMIDXgN3P6ppQA6DdDochBP2ELJs2j/up3+susL3T5Ds
# Wed6ZqZRokcv4GexufnaghasrmWwO3S8DVGy1j2jzMNfxhFyaZkCvOEQnhZZ20E8
# /mc5rE3O2vkVwhCKLeUSgQpP01lKH9eyN6QLY86OCg2V3CPbG5cR7yMxkcJXK9KR
# B1+BKaRwfeWu5tMCAwEAAaN2MHQwEwYDVR0lBAwwCgYIKwYBBQUHAwMwXQYDVR0B
# BFYwVIAQau8EqHPgYg9/6FarRpOcIaEuMCwxKjAoBgNVBAMTIVBvd2VyU2hlbGwg
# TG9jYWwgQ2VydGlmaWNhdGUgUm9vdIIQhyqJO0WxH7dLs1MlAkXI0TAJBgUrDgMC
# HQUAA4IBAQBWOP2UsXkGzOKzraObE5DwUX0adeKFfDr1qnSxQAiFXUg3gjg7siUI
# 01p7pFwakPNFptd+f6e8AB243PHtOJ9sNNUwpbcC4OfBwbUrZSNdW9F0wB6M0LVu
# qFzOIiqGUdz42O0gz7CDBb51vUVb9N3IDOZYunUTuxpuBvn8AYTSrKX1hE68vtuY
# 0z57YTaYIIobO9N7whKw44rtSa2MEQBoH4kmZDos+P9dEsZpHRxygwmMJ/mri00j
# PIvmCHTYC3P9VJyt418KKaPZ30+B64agbkkMdaSc+mZyUQtDmYcO2mp2TEha/DpY
# DmVIDgxYOeDKbMm/TRb8wrFtwaq8Oa8lMYIB4TCCAd0CAQEwQDAsMSowKAYDVQQD
# EyFQb3dlclNoZWxsIExvY2FsIENlcnRpZmljYXRlIFJvb3QCEJdJDfpJTt2eS5eX
# a+DtzXgwCQYFKw4DAhoFAKB4MBgGCisGAQQBgjcCAQwxCjAIoAKAAKECgAAwGQYJ
# KoZIhvcNAQkDMQwGCisGAQQBgjcCAQQwHAYKKwYBBAGCNwIBCzEOMAwGCisGAQQB
# gjcCARUwIwYJKoZIhvcNAQkEMRYEFOLNtyBpSxAwnryB0X9ELtK9mymLMA0GCSqG
# SIb3DQEBAQUABIIBACKTsza9SvNy42rKq4YDcubGWBD11k/TC5QD7LzQhT/X4J0p
# ljc6YGZ97A31UWj5PHLTmX2rmcLxaMc+ju1KrKk9x+26udLXaHE94/Ganyk9IUG7
# oeP+fRtzgSXd1D+Bvg8bCcIamB/pkKfypsYS/jFUM+aIJs8tmgevtBz8LA6oCOm1
# z1G1ZqA7NAgZ69kJSDn/MFOin/fNouyMgmOEWjzyhC4RjK8DTwgxC3Y4AQhj+T9W
# 8u3rufOwqWi6xacOlAJMjxqnbVXlcXKO2hyTvXooujWH8oW8ltlyqp4zXBf/KeO9
# mdkcjmxku1IbfcF27P2E3W4BVrGSaTqfosC2q6A=
# SIG # End signature block
