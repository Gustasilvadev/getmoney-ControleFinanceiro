import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container:{
        flex:1,
        gap:80,
        backgroundColor:'#009490',
    },

    form:{
        flex:1,
        backgroundColor:'#F7EFEFD9',
        borderTopStartRadius:50,
        borderTopEndRadius:50,
        paddingHorizontal: 25,
        paddingVertical: 35,
        width: '100%',
        shadowColor: '#000',
        shadowOffset: { width: 0, height:4 },
        shadowOpacity: 5,
        shadowRadius: 20,
        elevation: 3,
        alignItems:"center",
        gap:70,
    },

    title:{
        color:'#009490',
        fontFamily: FONTS.montserrat.bold,
        fontSize:32,
    },

    formInput:{
        gap:20,
        width:"100%",

    },
    input:{
        backgroundColor:'#FFFFFF',
        borderRadius:40,
        height:"12%",
        padding:10,
        fontFamily: FONTS.openSans.regular,
        fontSize:16,
        borderWidth: 1,
        borderColor:'#009490',
    },

    options:{
        justifyContent:"center",
        alignItems:"center",
        gap:15,
    },

    textOptions:{
        color:'#009490',
        fontFamily: FONTS.openSans.regular,
        fontSize:16,
    },

    button:{
        width:"40%",
        height:"30%",
        backgroundColor:'#009490',
        borderRadius:15,
        justifyContent:"center",
        alignItems:"center",
    },

    buttonText:{
        color:'#FFFFFF',
        fontSize:18,
        fontFamily:FONTS.montserrat.bold,
    },

});