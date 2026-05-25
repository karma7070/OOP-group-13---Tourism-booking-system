// ============================================
// API SERVICE (Simulated Backend)
// ============================================

const ApiService = {
    // Simulated database
    _database: {
        users: [],
        bookings: [],
        payments: [],
        services: [
            {
                id: 'mount-cameroon',
                name: 'Mount Cameroon',
                type: 'DESTINATION',
                location: 'Buea, Southwest Region',
                price: 25000,
                rating: 4.8,
                reviews: 234,
                image: 'https://images.unsplash.com/photo-1590523277543-a94d2e4eb00b?w=600',
                description: 'Experience Africa\'s highest peak with guided hiking tours.',
                availability: true,
                features: ['Hiking', 'Adventure', '2-3 Days']
            },
            {
                id: 'kribi-beach',
                name: 'Kribi Beach',
                type: 'DESTINATION',
                location: 'South Region',
                price: 35000,
                rating: 4.9,
                reviews: 312,
                image: 'https://images.unsplash.com/photo-1580062089779-3a21e2d6ff37?w=600',
                description: 'Relax on pristine white sandy beaches by the Atlantic Ocean.',
                availability: true,
                features: ['Beach', 'Swimming', 'Seafood']
            },
            {
                id: 'waza-park',
                name: 'Waza National Park',
                type: 'DESTINATION',
                location: 'Far North Region',
                price: 50000,
                rating: 4.6,
                reviews: 187,
                image: 'https://images.unsplash.com/photo-1544735716-392fe2489ffa?w=600',
                description: 'Discover diverse wildlife including lions, elephants, and giraffes.',
                availability: true,
                features: ['Safari', 'Wildlife', 'Photography']
            },
            {
                id: 'foumban-palace',
                name: 'Foumban Royal Palace',
                type: 'DESTINATION',
                location: 'West Region',
                price: 15000,
                rating: 4.7,
                reviews: 156,
                image: 'https://images.unsplash.com/photo-1518509562904-e7ef99cdcc86?w=600',
                description: 'Visit the historic palace and museum of the Bamoun Kingdom.',
                availability: true,
                features: ['History', 'Culture', 'Museum']
            },
            {
                id: 'hilton-yaounde',
                name: 'Hilton Yaoundé',
                type: 'HOTEL',
                location: 'Yaoundé Centre',
                price: 85000,
                rating: 4.5,
                image: 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600',
                description: 'Luxury hotel in the heart of Yaoundé',
                amenities: ['Free WiFi', 'Pool', 'Gym', 'Parking'],
                stars: 5
            },
            {
                id: 'sawa-douala',
                name: 'Sawa Hotel Douala',
                type: 'HOTEL',
                location: 'Bonanjo, Douala',
                price: 120000,
                rating: 4.7,
                image: 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=600',
                description: 'Business hotel with premium amenities',
                amenities: ['Free WiFi', 'Spa', 'Restaurant', 'Room Service'],
                stars: 4
            },
            {
                id: 'adventure-tour',
                name: 'Adventure Cameroon Tour',
                type: 'TOUR',
                price: 250000,
                duration: '7 Days / 6 Nights',
                itinerary: 'Mount Cameroon - Limbe - Kribi - Douala',
                image: 'https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?w=600',
                description: 'Complete adventure experience across Cameroon',
                highlights: ['Hike Mount Cameroon', 'Visit Limbe Wildlife Centre', 'Beach day at Kribi', 'City tour of Douala'],
                discount: 15
            },
            {
                id: 'cultural-tour',
                name: 'Cultural Heritage Tour',
                type: 'TOUR',
                price: 180000,
                duration: '5 Days / 4 Nights',
                itinerary: 'Foumban - Bafoussam - Bamenda - Dschang',
                image: 'https://images.unsplash.com/photo-1488646953014-85cb44e25828?w=600',
                description: 'Immerse yourself in Cameroon\'s rich cultural heritage',
                highlights: ['Foumban Royal Palace', 'Traditional crafts', 'Bamenda highlands', 'Dschang Museum'],
                discount: 10
            }
        ]
    },
    
    /**
     * Simulate API delay
     */
    async _delay(ms = 500) {
        return new Promise(resolve => setTimeout(resolve, ms));
    },
    
    /**
     * Register user
     */
    async register(userData) {
        await this._delay();
        const exists = this._database.users.find(u => u.email === userData.email);
        if (exists) {
            throw new Error('Email already registered');
        }
        const newUser = {
            id: generateId('USER'),
            ...userData,
            role: CONSTANTS.ROLES.TOURIST,
            createdAt: new Date().toISOString()
        };
        this._database.users.push(newUser);
        return { success: true, user: newUser };
    },
    
    /**
     * Login user
     */
    async login(email, password) {
        await this._delay();
        const user = this._database.users.find(u => u.email === email && u.password === password);
        if (!user) {
            throw new Error('Invalid credentials');
        }
        StorageService.set('currentUser', user);
        return { success: true, user };
    },
    
    /**
     * Search services
     */
    async search(query) {
        await this._delay(300);
        let results = this._database.services;
        
        if (query.keyword) {
            const keyword = query.keyword.toLowerCase();
            results = results.filter(s => 
                s.name.toLowerCase().includes(keyword) ||
                s.location?.toLowerCase().includes(keyword) ||
                s.description?.toLowerCase().includes(keyword)
            );
        }
        
        if (query.type) {
            results = results.filter(s => s.type === query.type);
        }
        
        return results;
    },
    
    /**
     * Get service by ID
     */
    async getService(id) {
        await this._delay(200);
        const service = this._database.services.find(s => s.id === id);
        if (!service) throw new Error('Service not found');
        return service;
    },
    
    /**
     * Create booking
     */
    async createBooking(bookingData) {
        await this._delay();
        const booking = {
            id: generateId('BOOK'),
            ...bookingData,
            status: CONSTANTS.BOOKING_STATUS.PENDING,
            createdAt: new Date().toISOString()
        };
        this._database.bookings.push(booking);
        return booking;
    },
    
    /**
     * Process payment
     */
    async processPayment(paymentData) {
        await this._delay(1000);
        const success = Math.random() > 0.1; // 90% success rate
        if (success) {
            const payment = {
                id: generateId('PAY'),
                ...paymentData,
                status: CONSTANTS.PAYMENT_STATUS.COMPLETED,
                processedAt: new Date().toISOString()
            };
            this._database.payments.push(payment);
            return payment;
        }
        throw new Error('Payment failed. Please try again.');
    },
    
    /**
     * Cancel booking
     */
    async cancelBooking(bookingId) {
        await this._delay();
        const booking = this._database.bookings.find(b => b.id === bookingId);
        if (!booking) throw new Error('Booking not found');
        booking.status = CONSTANTS.BOOKING_STATUS.CANCELLED;
        return booking;
    }
};
