from monitor.system import monitor_system
from monitor.filesystem import start_file_watch
from monitor.git_monitor import monitor_git
from monitor.process import monitor_processes
import threading
import time

print("🛰️ Starting GitHub Codespace Monitor...\n")

threading.Thread(target=monitor_system, daemon=True).start()
threading.Thread(target=monitor_git, daemon=True).start()
threading.Thread(target=monitor_processes, daemon=True).start()
start_file_watch()

try:
    while True:
        time.sleep(1)
except KeyboardInterrupt:
    print("\n🛑 Shutting down monitor...")