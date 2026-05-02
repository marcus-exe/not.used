interface ProjectCardProps {
  title: string;
  description: string;
  technologies: string[];
  liveUrl?: string;
  githubUrl?: string;
}

export default function ProjectCard({
  title,
  description,
  technologies,
  liveUrl,
  githubUrl,
}: ProjectCardProps) {
  return (
    <div className="group relative bg-card border border-border rounded-lg p-6 hover:border-accent-purple/50 transition-all duration-300">
      {/* Gradient glow effect on hover */}
      <div className="absolute inset-0 bg-gradient-to-br from-accent-purple/0 to-accent-blue/0 group-hover:from-accent-purple/5 group-hover:to-accent-blue/5 rounded-lg transition-all duration-300" />
      
      <div className="relative z-10">
        <h3 className="text-xl font-bold text-foreground mb-3 group-hover:text-accent-purple transition-colors">
          {title}
        </h3>
        
        <p className="text-foreground/70 text-sm mb-4 line-clamp-3">
          {description}
        </p>
        
        <div className="flex flex-wrap gap-2 mb-4">
          {technologies.map((tech, index) => (
            <span
              key={index}
              className="text-xs px-3 py-1 bg-accent-purple/10 text-accent-purple rounded-full border border-accent-purple/20"
            >
              {tech}
            </span>
          ))}
        </div>
        
        <div className="flex gap-4">
          {liveUrl && (
            <a
              href={liveUrl}
              target="_blank"
              rel="noopener noreferrer"
              className="text-sm text-accent-purple hover:text-accent-blue transition-colors font-medium"
            >
              View Live →
            </a>
          )}
          {githubUrl && (
            <a
              href={githubUrl}
              target="_blank"
              rel="noopener noreferrer"
              className={`text-sm transition-colors font-medium ${
                liveUrl 
                  ? 'text-foreground/60 hover:text-foreground' 
                  : 'text-accent-purple hover:text-accent-blue'
              }`}
            >
              View on GitHub →
            </a>
          )}
        </div>
      </div>
    </div>
  );
}

