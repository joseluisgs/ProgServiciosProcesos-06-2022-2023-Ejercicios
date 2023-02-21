#!/usr/bin/env bash

## Llavero Servidor:
keytool -genkeypair -alias serverKeyPair -keyalg RSA -keysize 4096 -validity 365 -storetype PKCS12 -keystore server_keystore.p12 -storepass empresaktor

## Llavero Cliente:
keytool -genkeypair -alias clientKeyPair -keyalg RSA -keysize 4096 -validity 365 -storetype PKCS12 -keystore client_keystore.p12 -storepass empresaktor