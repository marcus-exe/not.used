'use client';

import { useState } from 'react';
import Image from 'next/image';
import ProjectCard from '@/components/ProjectCard';
import AnimatedHero from '@/components/AnimatedHero';

export default function Home() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    message: '',
  });

  const projects = [
    {
      title: 'Vinheria App',
      description: 'Wine shop application developed at FIAP showcasing mobile development with modern Android architecture. Built with Kotlin, implementing best practices in mobile app development.',
      technologies: ['Kotlin', 'Android Jetpack', 'Mobile Development'],
      githubUrl: 'https://github.com/marcus-exe/vinheria-app',
    },
    {
      title: 'Big Data Infrastructure Suite',
      description: 'Comprehensive big data infrastructure test suite built with Python. Designed for testing and validating big data pipelines and infrastructure components with Apache Spark.',
      technologies: ['Python', 'Apache Spark', 'Big Data', 'Testing'],
      githubUrl: 'https://github.com/marcus-exe/bigdata-infra-suite',
    },
    {
      title: 'Hive App',
      description: 'Flutter-based CRUD application with NoSQL database integration. Mobile application demonstrating cross-platform development capabilities with modern Flutter architecture.',
      technologies: ['Flutter', 'Dart', 'NoSQL', 'Mobile Development'],
      githubUrl: 'https://github.com/marcus-exe/hive_app',
    },
  ];

  const skills = [
    'Java', 'Kotlin', 'Python', 'C++', 'C#', 'R',
    'SpringBoot', '.NET', 'Node.js', 'Express', 'Android Jetpack',
    'Docker', 'Kubernetes', 'Terraform', 'AWS',
    'Jenkins', 'Apache Spark', 'n8n',
    'MySQL', 'PostgreSQL', 'OracleDB',
    'Linux', 'Shell Script', 'DevOps', 'Big Data', 'Generative AI'
  ];

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Handle form submission here
    console.log('Form submitted:', formData);
    alert('Thank you for your message! I will get back to you soon.');
    setFormData({ name: '', email: '', message: '' });
  };

  return (
    <main className="min-h-screen">
      {/* Animated Hero Section */}
      <AnimatedHero />

      {/* Projects Section */}
      <section id="projects" className="py-20 px-6 relative overflow-hidden">
        {/* Animated grid background */}
        <div className="absolute inset-0 opacity-20 pointer-events-none">
          <div className="absolute inset-0 grid-background" />
        </div>
        
        <div className="max-w-4xl mx-auto relative z-10">
          <div className="mb-12">
            <h2 className="text-4xl md:text-5xl font-bold mb-4">
              Featured <span className="gradient-text">Projects</span>
            </h2>
            <p className="text-foreground/70 text-lg max-w-2xl">
              A showcase of my work in Mobile Development, Data Engineering, and DevOps solutions.
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {projects.map((project, index) => (
              <ProjectCard key={index} {...project} />
            ))}
          </div>
        </div>
      </section>

      {/* About Section */}
      <section id="about" className="py-20 px-6 bg-card/30 relative overflow-hidden">
        {/* Animated grid background */}
        <div className="absolute inset-0 opacity-20 pointer-events-none">
          <div className="absolute inset-0 grid-background" />
        </div>
        
        <div className="max-w-4xl mx-auto relative z-10">
          {/* Header with Profile Image */}
          <div className="mb-12 flex flex-col md:flex-row items-start gap-6">
            {/* Title and Description */}
            <div className="flex-1">
              <h2 className="text-4xl md:text-5xl font-bold mb-4">
                About <span className="gradient-text">Me</span>
              </h2>
              <p className="text-foreground/70 text-lg max-w-2xl">
                Software engineer passionate about solving complex problems with elegant solutions.
              </p>
            </div>
            
            {/* Profile Image */}
            <div className="relative w-32 h-32 md:w-40 md:h-40 rounded-2xl overflow-hidden border-2 border-accent-purple/30 hover:border-accent-purple transition-colors flex-shrink-0">
              <Image
                src="/profile.jpeg"
                alt="Marcus Sena"
                fill
                className="object-cover"
                priority
              />
            </div>
          </div>
          
          <div className="grid md:grid-cols-2 gap-12">
            <div className="space-y-6">
              <p className="text-foreground/80 text-lg leading-relaxed">
              I'm a software developer focused on distributed systems and mobile development, blending expertise in Android (Kotlin, Jetpack Compose) and Flutter with hands-on experience in DevOps and cloud infrastructure.
              </p>
              
              <p className="text-foreground/80 text-lg leading-relaxed">
              I've worked with international and multicultural teams, contributing to global projects with a strong focus on quality, performance, and scalability. I enjoy building full-stack solutions — from mobile apps to infrastructure — leveraging modern practices like infrastructure as code, container orchestration, CI/CD pipelines, and data automation with Apache Airflow.
              </p>
              
              <p className="text-foreground/80 text-lg leading-relaxed">
                Fun fact: I'm a strong Obsidian defender! 📝
              </p>
            </div>
            
            <div>
              <h3 className="text-xl font-semibold mb-4 text-accent-purple">Skills & Technologies</h3>
              <div className="flex flex-wrap gap-2">
                {skills.map((skill, index) => (
                  <span
                    key={index}
                    className="px-4 py-2 bg-accent-purple/10 text-foreground border border-accent-purple/20 rounded-full text-sm"
                  >
                    {skill}
                  </span>
                ))}
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Contact Section */}
      <section id="contact" className="py-20 px-6 relative overflow-hidden">
        {/* Animated grid background */}
        <div className="absolute inset-0 opacity-20 pointer-events-none">
          <div className="absolute inset-0 grid-background" />
        </div>
        
        <div className="max-w-4xl mx-auto relative z-10">
          <div className="mb-12">
            <h2 className="text-4xl md:text-5xl font-bold mb-4">
              Get In <span className="gradient-text">Touch</span>
            </h2>
            <p className="text-foreground/70 text-lg max-w-2xl">
              Ready to collaborate on your next project or discuss opportunities.
            </p>
          </div>
          
          <div className="grid md:grid-cols-2 gap-12">
            <div>
              <p className="text-foreground/80 text-lg mb-8">
                I'm always open to new opportunities, collaborations, and interesting projects 
                in Mobile Development, Big Data, DevOps, or Generative AI. 
                Let's build something amazing together!
              </p>
              
              <div className="space-y-4">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 bg-accent-purple/10 rounded-lg flex items-center justify-center">
                    <span className="text-accent-purple">📧</span>
                  </div>
                  <div>
                    <p className="text-sm text-foreground/60">Email</p>
                    <a href="mailto:marcus.sena@pm.me" className="text-foreground hover:text-accent-purple transition-colors">
                      marcus.sena@pm.me
                    </a>
                  </div>
                </div>
                
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 bg-accent-purple/10 rounded-lg flex items-center justify-center">
                    <span className="text-accent-purple">💼</span>
                  </div>
                  <div>
                    <p className="text-sm text-foreground/60">LinkedIn</p>
                    <a href="https://www.linkedin.com/in/marcus-sena/" target="_blank" rel="noopener noreferrer" className="text-foreground hover:text-accent-purple transition-colors">
                      linkedin.com/in/marcus-sena
                    </a>
                  </div>
                </div>
                
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 bg-accent-purple/10 rounded-lg flex items-center justify-center">
                    <span className="text-accent-purple">💻</span>
                  </div>
                  <div>
                    <p className="text-sm text-foreground/60">GitHub</p>
                    <a href="https://github.com/marcus-exe" target="_blank" rel="noopener noreferrer" className="text-foreground hover:text-accent-purple transition-colors">
                      github.com/marcus-exe
          </a>
        </div>
                </div>
                
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 bg-accent-purple/10 rounded-lg flex items-center justify-center">
                    <span className="text-accent-purple">🌍</span>
                  </div>
                  <div>
                    <p className="text-sm text-foreground/60">Location</p>
                    <span className="text-foreground">Manaus, AM - Brazil</span>
                  </div>
                </div>
              </div>
            </div>
            
            <div>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                  <label htmlFor="name" className="block text-sm font-medium mb-2">
                    Name
                  </label>
                  <input
                    type="text"
                    id="name"
                    value={formData.name}
                    onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                    required
                    className="w-full px-4 py-3 bg-card border border-border rounded-lg focus:outline-none focus:border-accent-purple transition-colors text-foreground"
                    placeholder="Your name"
                  />
                </div>
                
                <div>
                  <label htmlFor="email" className="block text-sm font-medium mb-2">
                    Email
                  </label>
                  <input
                    type="email"
                    id="email"
                    value={formData.email}
                    onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                    required
                    className="w-full px-4 py-3 bg-card border border-border rounded-lg focus:outline-none focus:border-accent-purple transition-colors text-foreground"
                    placeholder="your@email.com"
                  />
                </div>
                
                <div>
                  <label htmlFor="message" className="block text-sm font-medium mb-2">
                    Message
                  </label>
                  <textarea
                    id="message"
                    value={formData.message}
                    onChange={(e) => setFormData({ ...formData, message: e.target.value })}
                    required
                    rows={5}
                    className="w-full px-4 py-3 bg-card border border-border rounded-lg focus:outline-none focus:border-accent-purple transition-colors text-foreground resize-none"
                    placeholder="Your message..."
                  />
                </div>
                
                <button
                  type="submit"
                  className="w-full px-8 py-4 bg-gradient-to-r from-accent-purple to-accent-blue text-white rounded-lg font-medium hover:opacity-90 transition-opacity"
                >
                  Send Message
                </button>
              </form>
            </div>
          </div>
    </div>
      </section>
    </main>
  );
}
