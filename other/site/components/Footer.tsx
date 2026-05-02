export default function Footer() {
  const currentYear = new Date().getFullYear();
  
  return (
    <footer className="border-t border-border mt-20">
      <div className="max-w-7xl mx-auto px-6 py-12">
        <div className="flex flex-col md:flex-row justify-between items-center gap-6">
          <div className="text-foreground/60 text-sm">
            © {currentYear} Marcus Sena. All rights reserved.
          </div>
          
          <div className="flex gap-6">
            <a
              href="https://github.com/marcus-exe"
              target="_blank"
              rel="noopener noreferrer"
              className="text-foreground/60 hover:text-accent-purple transition-colors text-sm"
            >
              GitHub
            </a>
            <a
              href="https://www.linkedin.com/in/marcus-sena/"
              target="_blank"
              rel="noopener noreferrer"
              className="text-foreground/60 hover:text-accent-purple transition-colors text-sm"
            >
              LinkedIn
            </a>
            <a
              href="https://x.com/marcusen_x"
              target="_blank"
              rel="noopener noreferrer"
              className="text-foreground/60 hover:text-accent-purple transition-colors text-sm"
            >
              Twitter
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
}

