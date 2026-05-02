'use client';

import { useEffect, useState } from 'react';

export default function AnimatedHero() {
  const [displayedText, setDisplayedText] = useState('');
  const [currentIndex, setCurrentIndex] = useState(0);
  const [showCursor, setShowCursor] = useState(true);
  
  const fullText = 'Engineering Scalable Solutions';
  const subtitle = 'Software Engineer specializing in Mobile Development, Big Data, DevOps & Generative AI';

  // ASCII Art variations that animate
  const asciiFrames = [
    `
    ╔══════════════════╗
    ║   MARCUS SENA    ║
    ╚══════════════════╝
    `,
    `
    ╔═══════════════════╗
    ║   < DEVELOPER />  ║
    ╚═══════════════════╝
    `,
    `
    ╔══════════════════╗
    ║    {CODE: ∞}     ║
    ╚══════════════════╝
    `,
  ];

  const [asciiFrame, setAsciiFrame] = useState(0);

  // Typewriter effect
  useEffect(() => {
    if (currentIndex < fullText.length) {
      const timeout = setTimeout(() => {
        setDisplayedText(fullText.slice(0, currentIndex + 1));
        setCurrentIndex(currentIndex + 1);
      }, 100);
      return () => clearTimeout(timeout);
    }
  }, [currentIndex, fullText]);

  // Blinking cursor
  useEffect(() => {
    const interval = setInterval(() => {
      setShowCursor((prev) => !prev);
    }, 500);
    return () => clearInterval(interval);
  }, []);

  // ASCII art animation
  useEffect(() => {
    const interval = setInterval(() => {
      setAsciiFrame((prev) => (prev + 1) % asciiFrames.length);
    }, 2000);
    return () => clearInterval(interval);
  }, []);

  return (
    <section id="home" className="min-h-screen flex items-center justify-center px-6 pt-20 relative overflow-hidden">
      {/* Animated grid background */}
      <div className="absolute inset-0 opacity-20">
        <div className="absolute inset-0 grid-background" />
      </div>

      <div className="max-w-5xl mx-auto text-center relative z-10">
        {/* ASCII Art */}
        <div className="mb-8 font-mono text-accent-purple text-sm md:text-base opacity-80 whitespace-pre animate-pulse">
          {asciiFrames[asciiFrame]}
        </div>

        {/* Main heading with typewriter effect */}
        <div className="mb-6">
          <h1 className="text-5xl md:text-7xl font-bold mb-6 leading-tight">
            <span className="gradient-text inline-block animate-float">
              {displayedText}
              {currentIndex < fullText.length && (
                <span className={`${showCursor ? 'opacity-100' : 'opacity-0'}`}>|</span>
              )}
            </span>
          </h1>
        </div>

        {/* Animated subtitle */}
        <p className="text-lg md:text-xl text-foreground/70 mb-12 max-w-3xl mx-auto leading-relaxed opacity-0 animate-fade-in-up">
          {subtitle}
        </p>

        {/* Terminal-style info */}
        <div className="mb-12 font-mono text-sm text-foreground/50 space-y-2 opacity-0 animate-fade-in-up" style={{ animationDelay: '0.5s', animationFillMode: 'forwards' }}>
          <div className="flex justify-center gap-2">
            <span className="text-accent-purple">$</span>
            <span className="typing-animation">whoami</span>
          </div>
          <div className="text-accent-blue">{'>'} marcus.sena @ Software Engineer</div>
          <div className="flex justify-center gap-2 mt-4">
            <span className="text-accent-purple">$</span>
            <span className="typing-animation-2">cat location.txt</span>
          </div>
          <div className="text-accent-blue">{'>'} Manaus, Brazil 🇧🇷</div>
        </div>


        {/* Scroll indicator
        <div className="absolute bottom-0 left-1/2 transform -translate-x-1/2 opacity-0 animate-fade-in-up" style={{ animationDelay: '1.5s', animationFillMode: 'forwards' }}>
          <div className="flex flex-col items-center gap-2 text-foreground/40 animate-bounce">
            <span className="text-xs font-mono">scroll down</span>
            <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
              <path d="M10 14L5 9L6.5 7.5L10 11L13.5 7.5L15 9L10 14Z" fill="currentColor"/>
            </svg>
          </div>
        </div> */}


        {/* CTA Buttons */}
        <div className="flex flex-col mb-30 sm:flex-row gap-4 justify-center opacity-0 animate-fade-in-up" style={{ animationDelay: '1s', animationFillMode: 'forwards' }}>
          <button
            onClick={() => {
              const element = document.getElementById('projects');
              element?.scrollIntoView({ behavior: 'smooth' });
            }}
            className="group px-8 py-4 bg-gradient-to-r from-accent-purple to-accent-blue text-white rounded-lg font-medium hover:opacity-90 transition-all transform hover:scale-105 relative overflow-hidden"
          >
            <span className="relative z-10">View Projects</span>
            <div className="absolute inset-0 bg-white opacity-0 group-hover:opacity-20 transition-opacity" />
          </button>
          <button
            onClick={() => {
              const element = document.getElementById('contact');
              element?.scrollIntoView({ behavior: 'smooth' });
            }}
            className="px-8 py-4 border-2 border-accent-purple text-accent-purple rounded-lg font-medium hover:bg-accent-purple/10 transition-all transform hover:scale-105"
          >
            Get in Touch
          </button>
        </div>

      </div>
    </section>
  );
}

