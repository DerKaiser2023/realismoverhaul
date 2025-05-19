import subprocess
import time
from monitor.logger import log_info, log_error

def monitor_git():
    last_commit = None
    while True:
        try:
            result = subprocess.run(['git', 'log', '-1', '--pretty=%H'], capture_output=True, text=True)
            commit_hash = result.stdout.strip()
            if commit_hash != last_commit:
                last_commit = commit_hash
                log_info(f"Git - New commit detected: {commit_hash}")
        except Exception as e:
            log_error(f"Git monitor failed: {e}")
        time.sleep(10)