## add-signature.ps1
## Signs a file
param([string] $file=$(throw "Please specify a 
filename.")) $cert = @(Get-ChildItem cert:\CurrentUser\My -
codesigning)[0] Set-AuthenticodeSignature $file $cert
# SIG # Begin signature block
# MIIFuQYJKoZIhvcNAQcCoIIFqjCCBaYCAQExCzAJBgUrDgMCGgUAMGkGCisGAQQB
# gjcCAQSgWzBZMDQGCisGAQQBgjcCAR4wJgIDAQAABBAfzDtgWUsITrck0sYpfvNR
# AgEAAgEAAgEAAgEAAgEAMCEwCQYFKw4DAhoFAAQU81dAXAXz8Xbvw5qhWFQUbliB
# m+2gggNCMIIDPjCCAiqgAwIBAgIQl0kN+klO3Z5Ll5dr4O3NeDAJBgUrDgMCHQUA
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
# gjcCARUwIwYJKoZIhvcNAQkEMRYEFH27mhyDgnyjXJX3Xhx0V79rknJDMA0GCSqG
# SIb3DQEBAQUABIIBAE3KIaUX/oK757f0Zqtd1q9hrPCXJqpVW9d7uJdZ9YWPuV8W
# UYMyUoXxjzh2uQf/2cxCCDuYZGPODCxfsjqEGn4EF1nJxytyA3HEuqfZtvoeDNm+
# 2T+Ah+fhTyo2Y4w/jcHQFxllEjNp/vzNN70A5AvZ6zTKrRfJO6ftSJiyfeomoHEP
# /dta8Kb0OJv3qHmk6jLWnBvAbqZBdKltC3HiHit5Jgnuv9X/Hu0tmlSg0riCEqME
# s0rlFaXZlrCfHhrIsGsnL7k0eFVuJoQBiePva6ApIToTduCOg+ltffKkc71CR5o4
# qXSkf/SvymzhDPVnkfO6scN46/yHvdZ5ywyRSso=
# SIG # End signature block
