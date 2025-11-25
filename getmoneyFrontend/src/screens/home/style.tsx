import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native"

export const styles = StyleSheet.create({
    scrollContainer:{
        flexGrow:1,
        paddingBottom:20,
    },

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

    transacaoContainer:{
        margin:20,
    },

    transacaoCard:{
        backgroundColor: '#FFFFFF',
        borderRadius: 12,
        minHeight: 120,
        padding: 16,
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "flex-start",
        shadowColor: '#000',
        shadowOffset: {width: 0,height: 2},
        shadowOpacity: 0.1,
        shadowRadius: 3,
        elevation: 3,
        borderLeftWidth: 6,
        borderLeftColor: '#136F6C',
    },

    contentLeft:{
        flex: 1,
        marginRight: 12,
        flexShrink: 1,
    },

    contentRight:{
        marginRight: 12,
        flexShrink: 0,
        alignItems: "flex-end",
        minWidth:80,
    },

    descricaoLinha: {
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "flex-start",
        marginBottom: 8,
    },

    descricao:{
        fontSize: 16,
        fontFamily: FONTS.openSans.bold,
        flex:1,
        marginRight: 12,
        color: '#1A202C',
        marginBottom: 4,
    },

    data: {
        fontFamily:FONTS.openSans.semiBold,
        fontSize: 14,
        color: '#718096',
        marginBottom: 8,
    },

    categoriaNome: {
        fontSize: 14,
        fontFamily:FONTS.openSans.semiBold,
        color: '#2D3748',
    },

    metaNome: {
        fontSize: 14,
        fontFamily:FONTS.openSans.semiBold,
        color: '#2D3748',
        marginBottom: 2,
    },

    valor: {
        fontSize: 18,
        fontFamily:FONTS.montserrat.semiBold,
    },

    valorReceita: {
        color: '#009490',
    
    },

    valorDespesa: {
        color: '#CC0102',
    },

    valorNeutro: {
        color: '#2D3748',
    },

    emptyText:{
        fontFamily:FONTS.montserrat.bold,
        color: '#535652',
        textAlign:"center",
        margin:30,
    },

});