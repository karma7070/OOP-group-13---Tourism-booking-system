// ============================================
// PAYMENT MODULE
// ============================================

const PaymentModule = {
    /**
     * Process payment
     */
    async processPayment() {
        const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked')?.value;
        
        if (!paymentMethod) {
            NotificationModule.show('Please select a payment method.', 'warning');
            return;
        }
        
        const booking = StorageService.get('currentBooking');
        if (!booking) {
            NotificationModule.show('No booking found. Please start a new booking.', 'error');
            closeModal('paymentModal');
            return;
        }
        
        const paymentData = {
            bookingId: booking.id,
            amount: booking.totalAmount,
            method: paymentMethod
        };
        
        // Show processing state
        const payBtn = document.querySelector('#paymentModal .btn-primary');
        payBtn.disabled = true;
        payBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
        
        try {
            const payment = await ApiService.processPayment(paymentData);
            
            closeModal('paymentModal');
            StorageService.remove('currentBooking');
            
            // Show success
            NotificationModule.show(
                `Payment successful! Booking #${booking.id} confirmed. Check your email for details.`,
                'success'
            );
            
            // Send confirmation (simulated)
            this.sendConfirmation(booking, payment);
        } catch (error) {
            NotificationModule.show(error.message, 'error');
        } finally {
            payBtn.disabled = false;
            payBtn.innerHTML = 'Pay Now <i class="fas fa-lock"></i>';
        }
    },
    
    /**
     * Send booking confirmation
     */
    sendConfirmation(booking, payment) {
        console.log('Confirmation sent for booking:', booking.id);
        console.log('Payment reference:', payment.id);
        
        // In real app, send email/SMS
    },
    
    /**
     * Validate payment details
     */
    validatePaymentDetails(details) {
        const errors = [];
        
        if (!details.method) errors.push('Payment method is required');
        if (!details.amount || details.amount <= 0) errors.push('Invalid amount');
        
        return {
            valid: errors.length === 0,
            errors
        };
    }
};
