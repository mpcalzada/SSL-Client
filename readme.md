# SSL Client / Server - Testing Tool

This tool contains sources to establish a TCP IP connection over TLS. Testing only.

## Certificate Creation.

Using Java Keytool you might be able to create the required TrustStores and KeyStores.

### Client TrustStores

root.cer
> keytool -import -alias rootDigiCert -file root.cer -storetype JKS -keystore rootDigi.truststore

cacerts.cer
> keytool -import -alias cacertDigiCert -file cacert.cer -storetype JKS -keystore cacertDigi.truststore

Both certificates
>keytool -import -alias cacertDigiCert -file cacert.cer -storetype JKS -keystore digiCert.truststore
>keytool -import -alias rootDigiCert -file root.cer -storetype JKS -keystore digiCert.truststore

# Configuration

### Program Arguments Example.
` -a localhost 8443 TLS_DHE_DSS_WITH_AES_256_CBC_SHA256 `

### VM Options Example
` -Djavax.net.ssl.trustStore="C:\Users\MarcoCalzada\Desktop\Nueva carpeta\clienttruststore.jks" -Djavax.net.ssl.trustStorePassword=changeit `