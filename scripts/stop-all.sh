#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

stop_by_port() {
  local port="$1"
  local pids
  pids=$(lsof -ti :$port || true)
  if [ -n "$pids" ]; then
    echo "Deteniendo procesos en puerto $port: $pids"
    echo "$pids" | xargs -r kill -9
  else
    echo "No hay procesos en puerto $port"
  fi
}

echo "Deteniendo servicios..."
stop_by_port 8761 || true
stop_by_port 8081 || true
stop_by_port 8082 || true
stop_by_port 8083 || true

echo "Estado actual de /eureka/apps (si Eureka sigue corriendo):"
curl -s http://localhost:8761/eureka/apps || echo "Eureka no responde"

echo "Hecho."
