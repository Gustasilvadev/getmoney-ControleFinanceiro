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
        backgroundColor:'#1EB3AE',
        margin:50,
        borderRadius:10,
        height:85,
        alignItems:"center",
        justifyContent:"center",
    },

    cardText:{
        color:'#FFFFFF',
        fontFamily:FONTS.montserrat.bold,
        fontSize:16,
    },
});