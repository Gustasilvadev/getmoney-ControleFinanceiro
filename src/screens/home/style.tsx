import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native"

export const styles = StyleSheet.create({
    container:{
        height:150,
        backgroundColor:'#009490',
        alignItems:"center",
        justifyContent:"center",
    },

    text:{
        fontFamily:FONTS.montserrat.bold,
        fontSize:20,
        color:'#FFFFFF'
    },

    cardTop:{
    },
});