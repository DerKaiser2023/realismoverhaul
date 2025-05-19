import psutil
import time
from monitor.config import MONITOR_INTERVAL
from monitor.logger import log_info

def monitor_system():
    while True:
        cpu = psutil.cpu_percent(interval=1)
        ram = psutil.virtual_memory().percent
        disk = psutil.disk_usage('/workspaces').percent
        log_info(f"System - CPU: {cpu}% | RAM: {ram}% | DISK: {disk}%")
        time.sleep(MONITOR_INTERVAL)