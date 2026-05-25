// ============================================
// MODAL MANAGEMENT
// ============================================

const ModalUI = {
    activeModals: [],
    
    /**
     * Open modal
     */
    openModal(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.classList.add('show');
            this.activeModals.push(modalId);
            document.body.style.overflow = 'hidden';
        }
    },
    
    /**
     * Close modal
     */
    closeModal(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.classList.remove('show');
            this.activeModals = this.activeModals.filter(id => id !== modalId);
            if (this.activeModals.length === 0) {
                document.body.style.overflow = '';
            }
        }
    },
    
    /**
     * Switch between modals
     */
    switchModal(closeId, openId) {
        this.closeModal(closeId);
        setTimeout(() => this.openModal(openId), 300);
    },
    
    /**
     * Close modal on outside click
     */
    init() {
        window.addEventListener('click', (event) => {
            if (event.target.classList.contains('modal')) {
                event.target.classList.remove('show');
                document.body.style.overflow = '';
            }
        });
        
        // Close on escape key
        window.addEventListener('keydown', (event) => {
            if (event.key === 'Escape' && this.activeModals.length > 0) {
                const lastModal = this.activeModals[this.activeModals.length - 1];
                this.closeModal(lastModal);
            }
        });
    }
};

// Expose modal functions globally
function openModal(id) { ModalUI.openModal(id); }
function closeModal(id) { ModalUI.closeModal(id); }
function switchModal(closeId, openId) { ModalUI.switchModal(closeId, openId); }
