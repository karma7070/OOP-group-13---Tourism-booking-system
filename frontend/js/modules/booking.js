
// ============================================
// BOOKING MODULE
// ============================================

const BookingModule = {
    currentService: null,
    
    /**
     * View service details
     */
    async viewDetails(serviceId) {
        try {
            const service = await ApiService.getService(serviceId);
            this.currentService = service;
            
            // Populate booking summary
            document.getElementById('bookingSummary').innerHTML = `
                <h4><i class="fas fa-receipt"></i> Booking Summary</h4>
                <div class="summary-details">
                    <p><strong>Service:</strong> ${service.name}</p>
                    <p><strong>Type:</strong> ${service.type}</p>
                    <p><strong>Location:</strong> ${service.location || service.itinerary}</p>
                    <p><strong>Price:</strong> ${formatCurrency(service.price)} ${service.type === 'HOTEL' ? '/night' : '/person'}</p>
                    ${service.features ? `<p><strong>Features:</strong> ${service.features.join(', ')}</p>` : ''}
                    ${service.amenities ? `<p><strong>Amenities:</strong> ${service.amenities.join(', ')}</p>` : ''}
                </div>
            `;
            
            openModal('bookingModal');
        } catch (error) {
            NotificationModule.show('Could not load service details.', 'error');
        }
    },
    
    /**
     * Book hotel
     */
    async bookHotel(hotelId) {
        await this.viewDetails(hotelId);
    },
    
    /**
     * Book tour
     */
    async bookTour(tourId) {
        await this.viewDetails(tourId);
    },
    
    /**
     * Process booking
     */
    async processBooking(event) {
        event.preventDefault();
        
        if (!AuthModule.isAuthenticated()) {
            NotificationModule.show('Please login to complete your booking.', 'warning');
            closeModal('bookingModal');
            openModal('loginModal');
            return;
        }
        
        const bookingData = {
            userId: AuthModule.currentUser.id,
            serviceId: this.currentService.id,
            serviceName: this.currentService.name,
            customerName: document.getElementById('bookingName').value,
            email: document.getElementById('bookingEmail').value,
            phone: document.getElementById('bookingPhone').value,
            travelers: parseInt(document.getElementById('bookingTravelers').value) || 1,
            requirements: document.getElementById('bookingRequirements').value,
            totalAmount: this.currentService.price * (parseInt(document.getElementById('bookingTravelers').value) || 1)
        };
        
        try {
            const booking = await ApiService.createBooking(bookingData);
            StorageService.set('currentBooking', booking);
            
            closeModal('bookingModal');
            
            // Show payment modal
            document.getElementById('paymentAmount').innerHTML = `
                <p>Total Amount:</p>
                <span>${formatCurrency(booking.totalAmount)}</span>
            `;
            openModal('paymentModal');
        } catch (error) {
            NotificationModule.show('Booking failed. Please try again.', 'error');
        }
    },
    
    /**
     * Cancel booking
     */
    async cancelBooking(bookingId) {
        if (!confirm('Are you sure you want to cancel this booking?')) return;
        
        try {
            await ApiService.cancelBooking(bookingId);
            NotificationModule.show('Booking cancelled successfully.', 'success');
        } catch (error) {
            NotificationModule.show('Could not cancel booking.', 'error');
        }
    },
    
    /**
     * View bookings
     */
    viewBookings() {
        if (!AuthModule.isAuthenticated()) {
            NotificationModule.show('Please login to view your bookings.', 'warning');
            openModal('loginModal');
            return;
        }
        
        // In real app, fetch bookings from API
        NotificationModule.show('Loading your bookings...', 'info');
    }
};

