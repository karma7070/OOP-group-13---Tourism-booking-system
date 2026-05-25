// ============================================
// NAVIGATION UI
// ============================================

const NavigationUI = {
    /**
     * Initialize navigation
     */
    init() {
        this.setupScrollListener();
        this.setupActiveLinks();
        this.setupBackToTop();
    },
    
    /**
     * Toggle mobile menu
     */
    toggleMenu() {
        const navMenu = document.getElementById('navMenu');
        navMenu.classList.toggle('active');
        
        // Animate hamburger
        const hamburger = document.querySelector('.hamburger');
        hamburger.classList.toggle('active');
    },
    
    /**
     * Setup scroll listener for navbar
     */
    setupScrollListener() {
        window.addEventListener('scroll', () => {
            const navbar = document.getElementById('navbar');
            const backToTop = document.getElementById('backToTop');
            
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
                backToTop?.classList.add('show');
            } else {
                navbar.classList.remove('scrolled');
                backToTop?.classList.remove('show');
            }
        });
    },
    
    /**
     * Setup active navigation links
     */
    setupActiveLinks() {
        const sections = document.querySelectorAll('section[id]');
        const navLinks = document.querySelectorAll('.nav-link');
        
        window.addEventListener('scroll', () => {
            let current = '';
            
            sections.forEach(section => {
                const sectionTop = section.offsetTop - 100;
                if (window.scrollY >= sectionTop) {
                    current = section.getAttribute('id');
                }
            });
            
            navLinks.forEach(link => {
                link.classList.remove('active');
                if (link.getAttribute('href') === `#${current}`) {
                    link.classList.add('active');
                }
            });
        });
    },
    
    /**
     * Setup back to top button
     */
    setupBackToTop() {
        const button = document.getElementById('backToTop');
        if (button) {
            button.addEventListener('click', () => {
                window.scrollTo({ top: 0, behavior: 'smooth' });
            });
        }
    },
    
    /**
     * Scroll to top
     */
    scrollToTop() {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }
};
 