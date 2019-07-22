#!/bin/bash

CN=$1
SAN=$2

if [[ -z $CN ]]; then 
  CN='localhost'
fi

if [[ -z $SAN ]]; then
  SAN='IP:127.0.0.1'
fi

mkdir -p server/ ca/

 # Create private key for the Root Certificate Authority
 openssl genpkey \
   -out ca/private_key.pkcs8.pem \
   -algorithm RSA \
   -pkeyopt rsa_keygen_bits:2048

 # Self-sign the Root Certificate Authority
 openssl req \
   -x509 \
   -new \
   -nodes \
   -key ca/private_key.pkcs8.pem  \
   -days 3650 \
   -out ca/cacert.pem \
   -subj "/C=CA/ST=Ontario/L=Sample Company/O=Personal Team/CN=${CN}"

# Create private key for the server
openssl genpkey \
  -out server/private_key.pkcs8.pem \
  -algorithm RSA \
  -pkeyopt rsa_keygen_bits:2048

# Create a request for the Root CA to sign
openssl req \
  -new \
  -key server/private_key.pkcs8.pem  \
  -out server/csr.pem \
  -subj "/C=CA/ST=Ontario/L=Sample Company/O=Personal Team/CN=${CN}" \
  -addext "subjectAltName=${SAN}"

# Sign the request
openssl x509 \
  -req \
  -in server/csr.pem \
  -CA ca/cacert.pem \
  -CAkey ca/private_key.pkcs8.pem \
  -CAcreateserial \
  -out server/cert.pem \
  -days 3650
