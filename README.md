# Spring Batch Application

### Enfoques
- Tasklet: tareas manuales que se configuran en spring batch
- Chunk: tareas automatizadas y evitar configuración manual

## Mysql Database (on Docker)
docker run -p 3306:3306 --name batchDatabase -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=batchDatabase -d mysql
