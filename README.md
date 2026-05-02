# Marcus Sena - Portfolio Website

A modern, dark-themed portfolio website built with Next.js 15, TypeScript, and Tailwind CSS.

**Live Demo:** [Your deployed URL here]

Portfolio of Marcus Sena - Software Engineering student at FIAP, specializing in Mobile Development, Big Data, DevOps, and Generative AI.

## Features

- 🌑 **Dark Theme** - Sleek dark design with purple/blue accents
- 📱 **Fully Responsive** - Optimized for all devices and screen sizes
- ⚡ **Fast & Modern** - Built with Next.js 15 App Router and TypeScript
- 🎨 **Animated Hero** - ASCII art, typewriter effects, and terminal-style animations
- 🎯 **Animated Grid Background** - Subtle moving grid pattern across all sections
- 🚀 **Smooth Animations** - Professional transitions, hover effects, and micro-interactions
- 📝 **Contact Form** - Easy way for visitors to reach out with client-side validation
- 🖼️ **Profile Image** - Professional photo display with styled border effects

## Sections

1. **Hero/Home** - "Engineering Scalable Solutions" - Animated introduction with ASCII art, typewriter effect, terminal commands, and gradient text
2. **Projects** - Showcase your work with interactive project cards (3 featured projects from GitHub)
3. **About** - Profile picture, bio, and comprehensive skills display
4. **Contact** - Contact form with email, LinkedIn, and GitHub links

## Getting Started

### Install Dependencies

```bash
npm install
```

### Run Development Server

```bash
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser to see your portfolio.

### Build for Production

```bash
npm run build
npm start
```

## Customization Guide

### Add Your Profile Picture

1. Save your profile picture to the `public` folder as `profile.jpg`
   ```
   /public/profile.jpg
   ```
2. The image will automatically display in the About section with a rounded border and hover effect
3. Recommended size: At least 400x400px for best quality

### Update Personal Information

Edit `app/page.tsx` to customize:
- Projects array (lines 13-32) - Add your GitHub projects
- Skills array (lines 34-40) - Update with your tech stack
- About section text (lines 115-127) - Your bio and background
- Contact information (lines 160-250) - Email, LinkedIn, GitHub

### Change Colors

Edit `app/globals.css` to customize the theme:

```css
:root {
  --background: #0a0a0f;        /* Main background */
  --foreground: #e4e4e7;        /* Text color */
  --card-bg: #18181b;           /* Card backgrounds */
  --accent-purple: #8b5cf6;     /* Purple accent */
  --accent-blue: #3b82f6;       /* Blue accent */
  --border-color: #27272a;      /* Border color */
}
```

## What's Inside

### Projects Featured

- **Vinheria App** - Android app built with Kotlin and Android Jetpack
- **Big Data Infrastructure Suite** - Python test suite for big data pipelines with Apache Spark
- **Hive App** - Flutter CRUD application with NoSQL integration

### Technologies Showcased

**Languages:** Java, Kotlin, Python, C++, C#, R  
**Frameworks:** SpringBoot, .NET, Node.js, Express, Android Jetpack  
**Infrastructure:** Docker, Kubernetes, Terraform, AWS, Jenkins  
**Data & Tools:** Apache Spark, n8n, MySQL, PostgreSQL, OracleDB  
**Focus Areas:** Mobile Development, Big Data, DevOps, Generative AI

### Key Features

#### 1. Animated Hero Section
- **ASCII Art Animation** - Rotating frames with developer-themed designs
- **Typewriter Effect** - Text types out character by character with blinking cursor
- **Terminal Commands** - Styled command-line interface showing location and role
- **Floating Animations** - Smooth up/down motion on main heading
- **Animated Grid Background** - Moving grid pattern for depth

#### 2. Interactive Project Cards
- Hover effects with gradient glow
- Technology tags with custom styling
- Direct links to GitHub repositories
- Responsive grid layout (1 column mobile, 2 columns desktop)

#### 3. Professional About Section
- Profile image with rounded corners and purple border
- Side-by-side layout (image on right, content on left)
- Skills displayed as styled tags
- Multiple paragraph bio with spacing

#### 4. Contact Form
- Client-side validation
- Styled input fields matching dark theme
- Social media links (Email, LinkedIn, GitHub)
- Location badge showing Manaus, Brazil

## Tech Stack

- **Framework**: Next.js 15 (App Router with Static Export)
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **Fonts**: Geist Sans & Geist Mono
- **Deployment**: Cloudflare Pages (optimized), Vercel, or any static host

## Deployment

### Deploy to Cloudflare Pages

This site is configured for static export and optimized for Cloudflare Pages:

1. Push your code to GitHub
2. Go to [Cloudflare Pages](https://pages.cloudflare.com)
3. Connect your GitHub repository
4. Configure build settings:
   - **Build command:** `npm run build`
   - **Deploy command:** (leave empty)
   - **Output directory:** `out`
   - **Path:** `/`
5. Click "Save and Deploy"

### Deploy to Vercel

Alternative deployment using [Vercel](https://vercel.com):

1. Push your code to GitHub
2. Import your repository on Vercel
3. Vercel will automatically detect Next.js and deploy

### Other Platforms

This site can be deployed on any platform that supports static sites:
- Netlify
- AWS Amplify (S3 + CloudFront)
- GitHub Pages
- Any static hosting provider

## Project Structure

```
site/
├── app/
│   ├── layout.tsx          # Root layout with navigation and metadata
│   ├── page.tsx            # Main page with all sections
│   └── globals.css         # Global styles, theme, and animations
├── components/
│   ├── AnimatedHero.tsx    # Hero section with animations
│   ├── Navigation.tsx      # Fixed header with smooth scroll
│   ├── Footer.tsx          # Footer with social links
│   └── ProjectCard.tsx     # Project showcase cards
├── public/
│   └── profile.jpg         # Your profile picture (add this!)
├── package.json            # Dependencies
├── tsconfig.json           # TypeScript configuration
└── README.md              # This file
```

## Animation Details

The site includes several custom animations defined in `app/globals.css`:

- **grid-move** - Animated background grid that moves diagonally
- **float** - Smooth up/down floating effect for text
- **fade-in-up** - Sequential entrance animations with upward motion
- **typing** - Terminal-style typing animation for commands

All animations are performance-optimized and respect user preferences for reduced motion.

## License

Feel free to use this template for your own portfolio!
