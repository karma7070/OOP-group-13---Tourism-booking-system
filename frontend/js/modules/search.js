// ============================================
// SEARCH MODULE
// ============================================

const SearchModule = {
    currentTab: 'destinations',
    
    /**
     * Switch search tab
     */
    switchSearchTab(tab) {
        this.currentTab = tab;
        document.querySelectorAll('.search-tab').forEach(t => t.classList.remove('active'));
        event.target.classList.add('active');
    },
    
    /**
     * Search services
     */
    async searchServices() {
        const query = {
            keyword: document.getElementById('searchLocation').value,
            type: this.currentTab === 'destinations' ? 'DESTINATION' : 
                  this.currentTab === 'hotels' ? 'HOTEL' : 'TOUR',
            checkIn: document.getElementById('searchCheckIn').value,
            checkOut: document.getElementById('searchCheckOut').value,
            guests: document.getElementById('searchGuests').value
        };
        
        if (!query.keyword) {
            NotificationModule.show('Please enter a destination', 'warning');
            return;
        }
        
        try {
            const results = await ApiService.search(query);
            this.displayResults(results);
        } catch (error) {
            NotificationModule.show('Search failed. Please try again.', 'error');
        }
    },
    
    /**
     * Display search results
     */
    displayResults(results) {
        // Scroll to destinations section
        document.getElementById('destinations').scrollIntoView({ behavior: 'smooth' });
        
        const grid = document.querySelector('.destinations-grid');
        if (results.length === 0) {
            grid.innerHTML = `
                <div class="no-results" style="grid-column: 1/-1; text-align: center; padding: 3rem;">
                    <i class="fas fa-search" style="font-size: 3rem; color: #d1d5db;"></i>
                    <h3>No Results Found</h3>
                    <p>Try different keywords or browse our featured services below.</p>
                </div>
            `;
            return;
        }
        
        grid.innerHTML = results.map(service => this.createServiceCard(service)).join('');
    },
    
    /**
     * Create service card HTML
     */
    createServiceCard(service) {
        const badges = {
            'DESTINATION': { class: 'badge-popular', text: 'Popular' },
            'HOTEL': { class: 'badge-beach', text: 'Hotel' },
            'TOUR': { class: 'badge-cultural', text: 'Tour' }
        };
        
        const badge = badges[service.type] || badges['DESTINATION'];
        
        return `
            <div class="destination-card">
                <div class="card-image">
                    <img src="${service.image}" alt="${service.name}" loading="lazy">
                    <div class="card-overlay"></div>
                    <span class="card-badge ${badge.class}">${badge.text}</span>
                    <span class="card-rating"><i class="fas fa-star"></i> ${service.rating || '4.5'}</span>
                </div>
                <div class="card-content">
                    <div class="card-location">
                        <i class="fas fa-map-marker-alt"></i> ${service.location || service.itinerary || 'Cameroon'}
                    </div>
                    <h3 class="card-title">${service.name}</h3>
                    <p class="card-description">${service.description}</p>
                    <div class="card-footer">
                        <div class="price">
                            <span class="price-amount">${formatCurrency(service.price)}</span>
                        </div>
                        <button class="btn btn-primary btn-sm" onclick="viewDetails('${service.id}')">
                            View Details
                        </button>
                    </div>
                </div>
            </div>
        `;
    },
    
    /**
     * Load more destinations
     */
    async loadMoreDestinations() {
        const allDestinations = await ApiService.search({ type: 'DESTINATION' });
        this.displayResults(allDestinations);
        document.getElementById('destinations').scrollIntoView({ behavior: 'smooth' });
    }
};
