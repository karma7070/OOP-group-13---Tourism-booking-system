// ============================================
// DATA FORMATTERS
// ============================================

const Formatters = {
    /**
     * Format date
     */
    date(dateString, format = 'long') {
        const date = new Date(dateString);
        const options = {
            long: { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' },
            short: { year: 'numeric', month: 'short', day: 'numeric' },
            time: { hour: '2-digit', minute: '2-digit' }
        };
        return date.toLocaleDateString('en-US', options[format] || options.long);
    },
    
    /**
     * Format phone number
     */
    phone(number) {
        const cleaned = number.replace(/\D/g, '');
        if (cleaned.length === 9) {
            return `+237 ${cleaned.slice(0,3)} ${cleaned.slice(3,6)} ${cleaned.slice(6)}`;
        }
        return number;
    },
    
    /**
     * Truncate text
     */
    truncate(text, maxLength = 100) {
        if (text.length <= maxLength) return text;
        return text.substr(0, maxLength) + '...';
    },
    
    /**
     * Slugify text
     */
    slugify(text) {
        return text
            .toLowerCase()
            .replace(/[^\w\s-]/g, '')
            .replace(/[\s_-]+/g, '-')
            .replace(/^-+|-+$/g, '');
    },
    
    /**
     * Capitalize first letter
     */
    capitalize(text) {
        return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
    },
    
    /**
     * Get time ago
     */
    timeAgo(dateString) {
        const seconds = Math.floor((new Date() - new Date(dateString)) / 1000);
        const intervals = {
            year: 31536000,
            month: 2592000,
            week: 604800,
            day: 86400,
            hour: 3600,
            minute: 60
        };
        
        for (const [unit, secondsInUnit] of Object.entries(intervals)) {
            const interval = Math.floor(seconds / secondsInUnit);
            if (interval >= 1) {
                return `${interval} ${unit}${interval > 1 ? 's' : ''} ago`;
            }
        }
        return 'Just now';
    }
};
