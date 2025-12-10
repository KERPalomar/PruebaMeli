#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

echo "Haciendo ejecutables los wrappers mvnw si es necesario..."
chmod +x "$ROOT_DIR"/*/mvnw || true

start_service() {
  local dir="$1"
  local log="$2"
  echo "Iniciando $dir ..."
  nohup "$ROOT_DIR/$dir/mvnw" -f "$ROOT_DIR/$dir/pom.xml" spring-boot:run -DskipTests > "$ROOT_DIR/$dir/$log" 2>&1 &
  sleep 1
}

echo "Arrancando eureka-server"
start_service "eureka-server" "eureka.log"

echo "Esperando a que Eureka esté disponible (http://localhost:8761)..."
for i in {1..60}; do
  if curl -sSf http://localhost:8761/ >/dev/null 2>&1; then
    echo "Eureka disponible"
    break
  fi
  sleep 1
done

echo "Arrancando product-service-application"
start_service "product-service-application" "product.log"

echo "Arrancando comparison_service_appplication"
start_service "comparison_service_appplication" "comparison.log"

echo "Arrancando specification_service_application"
start_service "specification_service_application" "specification.log"

echo "Esperando registro en Eureka..."
sleep 3
curl -s http://localhost:8761/eureka/apps | sed -n '1,200p'

echo "Hecho. Revisa los logs dentro de cada módulo (ej: scripts/start-all.sh, tail -f <module>/<log>)"
