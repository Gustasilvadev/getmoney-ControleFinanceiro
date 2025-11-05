import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({

    modalOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        justifyContent: 'center',
        alignItems: 'center',
    },

    modalContainer: {
        backgroundColor: '#FFFFFF',
        borderRadius: 12,
        width: '90%',
        maxHeight: '80%',
        overflow: 'hidden',
    },

    modalHeader: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: 16,
        borderBottomWidth: 1,
        borderBottomColor: '#E5E5E5',
    },

    modalTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#136F6C',
    },
    closeButton: {
        padding: 4,
    },

    closeButtonText: {
        fontSize: 18,
        color: '#666',
        fontWeight: 'bold',
    },

    modalContent: {
        maxHeight: 400,
        padding: 16,
    },

    modalFooter: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        padding: 16,
        borderTopWidth: 1,
        borderTopColor: '#E5E5E5',
        gap: 12,
    },

    section: {
        marginBottom: 20,
    },
    sectionTitle: {
        fontSize: 16,
        fontWeight: '600',
        marginBottom: 8,
        color: '#333',
    },

    radioGroup: {
        flexDirection: 'row',
        gap: 12,
    },

    radioButton: {
        flex: 1,
        padding: 12,
        borderRadius: 8,
        borderWidth: 1,
        borderColor: '#DDD',
        alignItems: 'center',
    },
    radioButtonSelected: {
        backgroundColor: '#136F6C',
        borderColor: '#136F6C',
    },
    radioText: {
        color: '#666',
        fontWeight: '500',
    },
    radioTextSelected: {
        color: '#FFFFFF',
    },
    pickerContainer: {
        borderWidth: 1,
        borderColor: '#DDD',
        borderRadius: 8,
        overflow: 'hidden',
    },

    picker: {
        width: '100%',
    },

    metaItem: {
        padding: 12,
        borderWidth: 1,
        borderColor: '#DDD',
        borderRadius: 8,
        marginBottom: 8,
    },
    metaItemSelected: {
        backgroundColor: '#E6F3F3',
        borderColor: '#136F6C',
    },
    metaText: {
        color: '#666',
    },
    
    metaTextSelected: {
        color: '#136F6C',
        fontWeight: '500',
    },

    cancelButton: {
        flex: 1,
        marginTop: 20,
        borderRadius: 8,
        borderWidth: 1,
        borderColor: '#136F6C',
        justifyContent:"center",
        alignItems: "center",
    },

    cancelButtonText: {
        color: '#136F6C',
        fontWeight: '600',
    },

});