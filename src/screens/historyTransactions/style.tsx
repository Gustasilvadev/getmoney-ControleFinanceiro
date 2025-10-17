import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({

     scrollContainer: {
        flexGrow: 1,
        paddingBottom: 20,
    },

    header: {
        justifyContent:"center",
        height: 180,
        backgroundColor: '#009490',
        paddingHorizontal: 20,
        paddingVertical: 25,
        
    },

    titleHeader: {
        alignItems: 'center',
        marginBottom: 10,
    },

    titleText: {
        fontFamily: FONTS.montserrat.bold,
        fontSize: 22,
        color: '#FFFFFF',
        textAlign: 'center',
    },

    periodContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        gap: 15,
    },

    periodLabel: {
        fontFamily: FONTS.montserrat.bold,
        fontSize: 18,
        color: '#FFFFFF',
    },

    selectorsContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 10,
    },

    pickerWrapperMes: {
        width: 130,
        borderWidth: 3,
        borderColor: '#FFFFFF',
        borderRadius: 8,
        backgroundColor: '#009490',
        overflow: "hidden",
    },

    pickerWrapperAno: {
        width:110,
        borderWidth: 3,
        borderColor: '#FFFFFF',
        borderRadius: 8,
        backgroundColor: '#009490',
        overflow: 'hidden',
    },

    pickerAno: {
        height: 50,
        width:100,
        color: '#FFFFFF',
        fontFamily: FONTS.openSans.semiBold,
        
    },
    pickerMes: {
        height: 50,
        width:130,
        color: '#FFFFFF',
        fontFamily: FONTS.openSans.semiBold,
    },

});