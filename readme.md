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

