// ============================================
// CONFIGURATION SETTINGS
// ============================================

const CONFIG = {
    // App Info
    APP_NAME: 'TourBook Cameroon',
    APP_VERSION: '1.0.0',
    APP_DESCRIPTION: 'Tourism Booking System',
    
    // API Endpoints
    API: {
        BASE_URL: 'https://api.tourbookcameroon.cm/v1',
        ENDPOINTS: {
            REGISTER: '/auth/register',
            LOGIN: '/auth/login',
            SEARCH: '/services/search',
            BOOK: '/booking/create',
            PAYMENT: '/payment/process',
            CANCEL: '/booking/cancel'
        }
    },
    
    // Feature Flags
    FEATURES: {
        ENABLE_WISHLIST: true,
        ENABLE_REVIEWS: true,
        ENABLE_NOTIFICATIONS: true
    },
    
    // Limits
    LIMITS: {
        MAX_SEARCH_RESULTS: 50,
        MAX_BOOKING_PER_USER: 10,
        SESSION_TIMEOUT: 3600000 // 1 hour
    }
};

// Freeze config to prevent modifications
Object.freeze(CONFIG);
