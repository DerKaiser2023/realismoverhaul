from rich.console import Console
from datetime import datetime

console = Console()

def log_info(message):
    console.print(f"[bold green][{timestamp()} INFO][/bold green] {message}")

def log_warn(message):
    console.print(f"[bold yellow][{timestamp()} WARN][/bold yellow] {message}")

def log_error(message):
    console.print(f"[bold red][{timestamp()} ERROR][/bold red] {message}")

def timestamp():
    return datetime.now().strftime("%Y-%m-%d %H:%M:%S")