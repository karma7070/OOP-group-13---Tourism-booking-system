// ============================================
// AUTHENTICATION MODULE
// ============================================

const AuthModule = {
    currentUser: null,
    
    /**
     * Initialize auth
     */
    init() {
        this.currentUser = StorageService.get('currentUser');
        this.updateUI();
    },
    
    /**
     * Handle login
     */
    async handleLogin(event) {
        event.preventDefault();
        
        const email = document.getElementById('loginEmail').value;
        const password = document.getElementById('loginPassword').value;
        
        // Validate
        const emailCheck = ValidationService.email(email);
        if (!emailCheck.valid) {
            NotificationModule.show(emailCheck.message, 'error');
            return;
        }
        
        try {
            const result = await ApiService.login(email, password);
            this.currentUser = result.user;
            StorageService.set('currentUser', result.user);
            
            NotificationModule.show('Login successful! Welcome back.', 'success');
            closeModal('loginModal');
            this.updateUI();
        } catch (error) {
            NotificationModule.show(error.message, 'error');
        }
    },
    
    /**
     * Handle registration
     */
    async handleRegister(event) {
        event.preventDefault();
        
        const userData = {
            name: document.getElementById('regName').value,
            email: document.getElementById('regEmail').value,
            phone: document.getElementById('regPhone').value,
            password: document.getElementById('regPassword').value
        };
        
        // Validate
        const emailCheck = ValidationService.email(userData.email);
        if (!emailCheck.valid) {
            NotificationModule.show(emailCheck.message, 'error');
            return;
        }
        
        const passwordCheck = ValidationService.password(userData.password);
        if (!passwordCheck.valid) {
            NotificationModule.show(passwordCheck.message, 'error');
            return;
        }
        
        try {
            const result = await ApiService.register(userData);
            this.currentUser = result.user;
            StorageService.set('currentUser', result.user);
            
            NotificationModule.show('Registration successful! Welcome to TourBook Cameroon.', 'success');
            closeModal('registerModal');
            this.updateUI();
        } catch (error) {
            NotificationModule.show(error.message, 'error');
        }
    },
    
    /**
     * Logout
     */
    logout() {
        this.currentUser = null;
        StorageService.remove('currentUser');
        NotificationModule.show('Logged out successfully.', 'info');
        this.updateUI();
    },
    
    /**
     * Update UI based on auth state
     */
    updateUI() {
        const navActions = document.querySelector('.nav-actions');
        const userMenu = document.getElementById('userMenu');
        const userDisplayName = document.getElementById('userDisplayName');
        
        if (this.currentUser) {
            // Show user menu
            navActions.querySelector('.btn-outline').style.display = 'none';
            navActions.querySelector('.btn-primary').style.display = 'none';
            userMenu.style.display = 'block';
            userDisplayName.textContent = this.currentUser.name.split(' ')[0];
        } else {
            // Show login/register buttons
            navActions.querySelector('.btn-outline').style.display = '';
            navActions.querySelector('.btn-primary').style.display = '';
            userMenu.style.display = 'none';
        }
    },
    
    /**
     * Toggle user dropdown
     */
    toggleUserDropdown() {
        const dropdown = document.getElementById('userDropdown');
        dropdown.classList.toggle('show');
    },
    
    /**
     * Check if user is authenticated
     */
    isAuthenticated() {
        return this.currentUser !== null;
    },
    
    /**
     * Check if user is admin
     */
    isAdmin() {
        return this.currentUser?.role === CONSTANTS.ROLES.ADMIN;
    }
};
