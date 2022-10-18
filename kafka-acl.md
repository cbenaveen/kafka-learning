## List all ACLs
```shell
kafka-acls.sh \
  --bootstrap-server localhost:9092 \
  --list \
  --command-config client.properties
```

## Topic Operations — Write
```shell
kafka-acls.sh \
  --bootstrap-server localhost:9093 \
  --add \
  --allow-principal User:admin \
  --operation Write \
  --topic '*' \
  --command-config client.properties
```

## Topic Operations — Read
```shell
  kafka-acls.sh \
    --bootstrap-server :9093 \
    --add \
    --allow-principal User:admin \
    --operation Read \
    --topic '*' \
    --command-config client.properties
 ```
