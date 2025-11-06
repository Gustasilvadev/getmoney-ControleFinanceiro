import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({

    container: {
        flex: 1,
    },

    header: {
        justifyContent: "center",
        height: 150,
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
        textAlign: "center",
    },

    containerForm: {
        flex: 1,
        marginTop: 30,
        marginLeft: 80,
        marginRight: 80,
    },

    form: {
        gap: 15,
    },

     botaoSalvar: {
        backgroundColor: '#136F6C',
        padding: 16,
        borderRadius: 8,
        alignItems: "center",
        marginTop: 20,
    },
    
    botaoSenha: {
        backgroundColor: '#136F6C',
        padding: 16,
        borderRadius: 8,
        alignItems: "center",
        marginTop: 10,
    },
    
    botaoDesabilitado: {
        backgroundColor: '#CCCCCC',
    },
    
    textoBotao: {
        color: '#FFFFFF',
        fontSize: 16,
        fontFamily:FONTS.montserrat.semiBold,
    },
    
    separador: {
        height: 2,
        backgroundColor: '#E0E0E0',
        marginVertical: 20,
    },

});