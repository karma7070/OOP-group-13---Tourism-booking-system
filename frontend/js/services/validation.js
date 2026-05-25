// ============================================
// FORM VALIDATION SERVICE
// ============================================

const ValidationService = {
    /**
     * Validate email
     */
    email(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return {
            valid: re.test(email),
            message: re.test(email) ? '' : 'Please enter a valid email address'
        };
    },
    
    /**
     * Validate password strength
     */
    password(password) {
        const checks = {
            length: password.length >= 8,
            uppercase: /[A-Z]/.test(password),
            lowercase: /[a-z]/.test(password),
            number: /[0-9]/.test(password),
            special: /[!@#$%^&*]/.test(password)
        };
        
        const valid = Object.values(checks).every(Boolean);
        let message = '';
        
        if (!checks.length) message = 'Password must be at least 8 characters';
        else if (!checks.uppercase) message = 'Password must contain an uppercase letter';
        else if (!checks.lowercase) message = 'Password must contain a lowercase letter';
        else if (!checks.number) message = 'Password must contain a number';
        
        return { valid, message };
    },
    
    /**
     * Validate phone number (Cameroon)
     */
    phone(phone) {
        const re = /^(\+237|00237)?[6][5-9][0-9]{7}$/;
        const cleaned = phone.replace(/\s+/g, '');
        return {
            valid: re.test(cleaned),
            message: re.test(cleaned) ? '' : 'Please enter a valid Cameroon phone number'
        };
    },
    
    /**
     * Validate required field
     */
    required(value, fieldName = 'This field') {
        const valid = value !== null && value !== undefined && value.toString().trim() !== '';
        return {
            valid,
            message: valid ? '' : `${fieldName} is required`
        };
    },
    
    /**
     * Validate date
     */
    date(dateString) {
        const date = new Date(dateString);
        const valid = !isNaN(date.getTime());
        return {
            valid,
            message: valid ? '' : 'Please enter a valid date'
        };
    },
    
    /**
     * Validate future date
     */
    futureDate(dateString) {
        const date = new Date(dateString);
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        const valid = date >= today;
        return {
            valid,
            message: valid ? '' : 'Date must be today or in the future'
        };
    }
};
