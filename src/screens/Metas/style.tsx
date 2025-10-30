import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    
    scrollContainer:{
        flexGrow:1,
        paddingBottom:20,
    },

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

    subtitle:{
        fontFamily:FONTS.montserrat.bold,
        fontSize:18,
        color:'#535652',
        marginLeft:20,
    },

    containerMetas:{
        marginTop:50,

    },

    cardMeta:{
        backgroundColor:'#FFFFFF',
        margin:20,
        borderRadius:12,
        height:100,
        padding:15,
        shadowColor: '#000',
        shadowOffset: {width: 0,height: 2},
        shadowOpacity: 0.1,
        shadowRadius: 3,
        elevation: 3,
        borderLeftWidth: 0,
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
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
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

    emptyText:{
        fontFamily:FONTS.montserrat.bold,
        color: '#535652',
        textAlign:"center",
        margin:30,
    },

    botao:{
        margin:40,
    }
});