# Mini ATM Simulator — Non-Maven Docker POC

## Run locally
```bash
javac MiniATM.java
java MiniATM
```
Demo PIN: `1234`; initial in-memory balance: INR 10,000.00.

## Build and run with Docker
```bash
docker build -t mini-atm-simulator:local .
docker run --rm -it mini-atm-simulator:local
```
Use `-it` because this is an interactive console program.

## Azure DevOps / Private ACR
The included `azure-pipelines.yml` compiles with `javac`, builds a Docker image, and pushes:
- `simplejavapocacr.azurecr.io/mini-atm-simulator:<Build.BuildId>`
- `simplejavapocacr.azurecr.io/mini-atm-simulator:latest`

It assumes the Azure DevOps service connection is `sc-azure-poc`, the agent pool is `Default`, and the self-hosted agent has Java 17, Docker, Azure CLI, access to the private ACR endpoint, and enough free disk space. Update the pool/demand if your registered agent differs.

## Important limitations
This is a console application, not a browser-based web application. Its PIN, balance, and transaction list are stored in memory and reset on restart. The PIN is hard-coded and this demo must not be used for real banking or financial activity.
