// ============================================
// MAIN APPLICATION INITIALIZATION
// ============================================

const App = {
    /**
     * Initialize the entire application
     */
    init() {
        console.log(`🚀 ${CONFIG.APP_NAME} v${CONFIG.APP_VERSION} initializing...`);
        
        // Initialize services
        AuthModule.init();
        ModalUI.init();
        NavigationUI.init();
        ComponentsUI.init();
        
        // Setup global event handlers
        this.setupGlobalEvents();
        
        console.log('✅ Application initialized successfully');
    },
    
    /**
     * Setup global event listeners
     */
    setupGlobalEvents() {
        // Handle window resize
        window.addEventListener('resize', debounce(() => {
            // Close mobile menu on resize
            const navMenu = document.getElementById('navMenu');
            if (window.innerWidth > 768 && navMenu.classList.contains('active')) {
                navMenu.classList.remove('active');
            }
        }, 250));
        
        // Handle online/offline
        window.addEventListener('online', () => {
            NotificationModule.show('Back online!', 'success');
        });
        
        window.addEventListener('offline', () => {
            NotificationModule.show('You are offline. Some features may be unavailable.', 'warning');
        });
    },
    
    /**
     * Handle errors globally
     */
    handleError(error, context = '') {
        console.error(`Error in ${context}:`, error);
        
        if (CONFIG.FEATURES.ENABLE_NOTIFICATIONS) {
            NotificationModule.show(
                'Something went wrong. Please try again.',
                'error'
            );
        }
    }
};

// Initialize app when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    App.init();
});

// Global error handler
window.addEventListener('error', (event) => {
    App.handleError(event.error, 'Global');
});

// Handle unhandled promise rejections
window.addEventListener('unhandledrejection', (event) => {
    App.handleError(event.reason, 'Promise');
});

// Expose global functions for HTML onclick handlers
function searchServices() { SearchModule.searchServices(); }
function switchSearchTab(tab) { SearchModule.switchSearchTab(tab); }
function viewDetails(id) { BookingModule.viewDetails(id); }
function bookHotel(id) { BookingModule.bookHotel(id); }
function bookTour(id) { BookingModule.bookTour(id); }
function processBooking(event) { BookingModule.processBooking(event); }
function processPayment() { PaymentModule.processPayment(); }
function handleLogin(event) { AuthModule.handleLogin(event); }
function handleRegister(event) { AuthModule.handleRegister(event); }
function logout() { AuthModule.logout(); }
function viewProfile() { NotificationModule.show('Profile page coming soon!', 'info'); }
function viewBookings() { BookingModule.viewBookings(); }
function toggleMenu() { NavigationUI.toggleMenu(); }
function toggleUserDropdown() { AuthModule.toggleUserDropdown(); }
function loadMoreDestinations() { SearchModule.loadMoreDestinations(); }
function scrollToTop() { NavigationUI.scrollToTop(); }

function subscribeNewsletter(event) {
    event.preventDefault();
    const email = event.target.querySelector('input').value;
    if (email) {
        NotificationModule.show('Thank you for subscribing!', 'success');
        event.target.reset();
    }
}

function submitContactForm(event) {
    event.preventDefault();
    NotificationModule.show('Message sent successfully! We will get back to you soon.', 'success');
    event.target.reset();
}
