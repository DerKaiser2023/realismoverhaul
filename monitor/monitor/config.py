import os

CODESPACE_PATH = os.getenv("MONITOR_PATH", os.getcwd())
MONITOR_INTERVAL = int(os.getenv("MONITOR_INTERVAL", 5))  # seconds