import { FONTS } from "@/src/constants/fonts"
import { StyleSheet } from "react-native"

export const styles = StyleSheet.create({
    container:{
        flex:1,
    },

    header: {
        justifyContent:"center",
        height:150,
        backgroundColor: '#009490',
        paddingHorizontal: 20,
        paddingVertical: 25,    
    },
    
    titleHeader: {
        alignItems: "center",
        marginBottom: 10,
    },

    titleText: {
        fontFamily: FONTS.montserrat.bold,
        fontSize: 22,
        color: '#FFFFFF',
        textAlign:"center",
    },

    containerForm:{
        flex:1,
        marginTop:30,
        marginLeft:80,
        marginRight:80,
    },

    form:{
        gap:20,
    },

    text:{
        color:'#858587',
        fontFamily:FONTS.openSans.bold,
        fontSize:16,
        marginTop:0,
    },

    inputContainer: {
        height:75,
        backgroundColor: '#FFFFFF',
        borderRadius: 10,
        paddingTop: 20,
        paddingHorizontal: 10,
        paddingVertical: 10,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: "center",

    },

    label: {
        position: 'absolute',
        top: 6,
        left: 12,
        color: '#606062',
        fontFamily: FONTS.openSans.bold,
        fontSize: 16,
    },


    input: {
        borderWidth: 0,
        fontSize: 14,
        padding: 0, 
        margin: 0,
        fontFamily:FONTS.openSans.semiBold,
        color:'#858587',
       
    },

    iconRight: {
        marginLeft: "auto",
    },

    underline: {
        width:'100%',
        height: 3,
        backgroundColor: '#136F6C',
        position: "absolute",
        bottom: 10,
        left: 12,
    },


    sectionTitle: {
        color: '#858587',
        fontFamily: FONTS.openSans.bold,
        fontSize: 16,
    },

    checkboxGroup: {
        flexDirection: 'row',
        backgroundColor: '#F8F8F8',
        borderRadius: 10,
        padding: 8,   
    },

    checkboxItem: {
        flexDirection: 'row',
        alignItems: 'center',
        borderRadius: 8,
        paddingHorizontal: 20,
        paddingVertical: 12,
        flex: 1,
        justifyContent: 'center',
        marginHorizontal: 4,
    },

    checkboxItemSelected: {
        backgroundColor: '#FFFFFF',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
    },

    checkboxCircle: {
        width: 20,
        height: 20,
        borderRadius: 10,
        borderWidth: 2,
        borderColor: '#136F6C',
        marginRight: 10,
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: 'transparent',
    },

    checkboxCircleSelected: {
        backgroundColor: '#136F6C',
    },

    checkIcon: {
        display: 'none',
    },

    checkIconSelected: {
        display: 'flex',
    },

    checkboxText: {
        color: '#858587',
        fontFamily: FONTS.openSans.semiBold,
        fontSize: 14,
    },

    comboboxWrapper: {
        flexDirection: 'row',
        alignItems: 'center',
        flex: 1,
    },

    dropdownIcon: {
        marginLeft: 10,
    },

    modalOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        justifyContent: "center",
        alignItems: "center",
        padding: 20,
    },
    modalContent: {
        backgroundColor: '#FFFFFF',
        borderRadius: 15,
        padding: 20,
        width: '90%',
        maxHeight: '80%',
        shadowColor: '#000',
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5,
    },
    modalTitle: {
        fontSize: 18,
        fontFamily: FONTS.openSans.bold,
        color: '#136F6C',
        textAlign: 'center',
        marginBottom: 20,
    },

    modalBody: {
        minHeight: 200, // ← ADICIONE
    },

    novaCategoriaButton: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#F8F8F8',
        borderRadius: 10,
        padding: 15,
        marginBottom: 15,
        borderWidth: 1,
        borderColor: '#E8E8E8',
    },
    novaCategoriaText: {
        marginLeft: 10,
        fontSize: 16,
        fontFamily: FONTS.openSans.semiBold,
        color: '#136F6C',
    },
    categoriasTitle: {
        fontSize: 16,
        fontFamily: FONTS.openSans.bold,
        color: '#606062',
        marginBottom: 10,
    },
    categoriasList: {
        maxHeight: 200,
        marginBottom: 15,
    },
    categoriaItem: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: 12,
        borderBottomWidth: 1,
        borderBottomColor: '#F0F0F0',
    },
    categoriaText: {
        fontSize: 16,
        fontFamily: FONTS.openSans.semiBold,
        color: '#000000',
        flex: 1,
    },
    modalInput: {
        backgroundColor: '#FFFFFF',
        borderRadius: 10,
        borderWidth: 1,
        borderColor: '#E0E0E0',
        padding: 15,
        fontSize: 16,
        fontFamily: FONTS.openSans.semiBold,
        color: '#333333',
        marginBottom: 15,
    },
    modalButtons: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        gap: 10,
    },
    modalButton: {
        flex: 1,
        backgroundColor: '#F8F8F8',
        borderRadius: 10,
        padding: 15,
        alignItems: 'center',
        borderWidth: 1,
        borderColor: '#E8E8E8',
        minHeight: 50,
    },
    primaryButton: {
        backgroundColor: '#136F6C',
        borderColor: '#136F6C',
    },
    closeButton: {
        backgroundColor: '#F8F8F8',
        borderColor: '#E8E8E8',
    },
    modalButtonText: {
        fontSize: 16,
        fontFamily: FONTS.openSans.semiBold,
        color: '#333333',
    },
    primaryButtonText: {
        color: '#FFFFFF',
        fontSize: 16,
        fontFamily: FONTS.openSans.semiBold,
    },
    textoAviso: {
        textAlign: 'center',
        color: '#858587',
        fontFamily: FONTS.openSans.regular,
        fontStyle: 'italic',
        marginVertical: 20,
        fontSize: 14,
    },
});