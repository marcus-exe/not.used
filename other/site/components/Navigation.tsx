'use client';

import { useState, useEffect } from 'react';

export default function Navigation() {
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 20);
    };
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const scrollToSection = (id: string) => {
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <nav
      className={`fixed top-0 left-0 right-0 z-50 transition-all duration-300 ${
        isScrolled ? 'bg-background/80 backdrop-blur-md border-b border-border' : ''
      }`}
    >
      <div className="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
        <button
          onClick={() => scrollToSection('home')}
          className="text-xl font-bold gradient-text hover:opacity-80 transition-opacity"
        >
          Marcus Sena
        </button>
        
        <div className="flex gap-8">
          <button
            onClick={() => scrollToSection('projects')}
            className="text-foreground/70 hover:text-foreground transition-colors text-sm font-medium"
          >
            Projects
          </button>
          <button
            onClick={() => scrollToSection('about')}
            className="text-foreground/70 hover:text-foreground transition-colors text-sm font-medium"
          >
            About
          </button>
          <button
            onClick={() => scrollToSection('contact')}
            className="text-foreground/70 hover:text-foreground transition-colors text-sm font-medium"
          >
            Contact
          </button>
        </div>
      </div>
    </nav>
  );
}

