import psutil
import time
from monitor.logger import log_info

def monitor_processes():
    while True:
        procs = [p.info for p in psutil.process_iter(['pid', 'name'])]
        log_info(f"Processes - {len(procs)} active processes")
        time.sleep(15)