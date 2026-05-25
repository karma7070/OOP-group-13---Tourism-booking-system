// ============================================
// UI COMPONENTS
// ============================================

const ComponentsUI = {
    /**
     * Initialize all components
     */
    init() {
        this.initPreloader();
        this.initAnimations();
        this.initWishlist();
    },
    
    /**
     * Preloader
     */
    initPreloader() {
        window.addEventListener('load', () => {
            setTimeout(() => {
                const preloader = document.getElementById('preloader');
                preloader?.classList.add('hidden');
            }, 1000);
        });
    },
    
    /**
     * Initialize scroll animations
     */
    initAnimations() {
        // Simple fade-in animation on scroll
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.style.opacity = '1';
                    entry.target.style.transform = 'translateY(0)';
                }
            });
        }, { threshold: 0.1 });
        
        document.querySelectorAll('[data-aos]').forEach(el => {
            el.style.opacity = '0';
            el.style.transform = 'translateY(30px)';
            el.style.transition = 'all 0.6s ease';
            observer.observe(el);
        });
    },
    
    /**
     * Toggle wishlist
     */
    toggleWishlist(serviceId) {
        if (!AuthModule.isAuthenticated()) {
            NotificationModule.show('Please login to add to wishlist.', 'warning');
            openModal('loginModal');
            return;
        }
        
        let wishlist = StorageService.get('wishlist', []);
        const index = wishlist.indexOf(serviceId);
        
        if (index > -1) {
            wishlist.splice(index, 1);
            NotificationModule.show('Removed from wishlist.', 'info');
        } else {
            wishlist.push(serviceId);
            NotificationModule.show('Added to wishlist!', 'success');
        }
        
        StorageService.set('wishlist', wishlist);
    },
    
    /**
     * Toggle password visibility
     */
    togglePassword(inputId) {
        const input = document.getElementById(inputId);
        const icon = input.parentElement.querySelector('.toggle-password');
        
        if (input.type === 'password') {
            input.type = 'text';
            icon.classList.replace('fa-eye', 'fa-eye-slash');
        } else {
            input.type = 'password';
            icon.classList.replace('fa-eye-slash', 'fa-eye');
        }
    }
};

// Make togglePassword globally accessible
function togglePassword(id) { ComponentsUI.togglePassword(id); }
function toggleWishlist(id) { ComponentsUI.toggleWishlist(id); }
