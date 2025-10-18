# Financial Backtester
Modular system for replaying historical market data, testing trading strategies,
and visualizing results.

## Structure
- **market-replay/** – Kafka producer + REST control API
- **strategy-engine/** – Trading algorithms & portfolio logic
- **front-end/** – Web dashboard
- **infra/** – Docker Compose, Ansible, Terraform, Kubernetes configs
- **data/** – Parquet datasets & data prep scripts
