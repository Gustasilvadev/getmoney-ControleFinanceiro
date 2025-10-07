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
        marginTop:30,
        marginBottom:30,
        marginLeft:80,
        marginRight:80,
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

    subtitle:{
        fontFamily:FONTS.montserrat.bold,
        fontSize:18,
        color:'#535652',
        marginLeft:20,
    },

    cardMeta:{
        backgroundColor:'#FFFFFF',
        margin:20,
        borderRadius:10,
        height:100,
        padding:15,

    },

    cardTitleMeta:{
        fontFamily:FONTS.montserrat.bold,
        fontSize:15,
        color:'#535652',
        marginLeft:10,
    },

    progressBar: {
        height: 10,
        backgroundColor: '#CDCDCD',
        borderRadius: 3,
        margin: 10,
    },

    progressFill: {
        height: '100%',
        backgroundColor: '#136F6C',
        borderRadius: 3,
        position:"absolute",
    },

    progressInfo:{
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginTop: 25,
    },

    cardSubTitleMeta:{
        position:"absolute",
        width: '100%',
        fontFamily:FONTS.openSans.semiBold,
        fontSize: 14,
        color: '#535652',
        zIndex: 1,
    },

    cardSubTitlePercent: {
        position:"absolute",
        fontFamily:FONTS.montserrat.bold,
        right: 10,
        fontSize: 14,
        color: '#136F6C',
        zIndex: 1,
    },

    emptyTextMeta:{
        fontFamily:FONTS.montserrat.bold,
        color: '#535652',
        textAlign:"center",
        margin:30,
    },

});