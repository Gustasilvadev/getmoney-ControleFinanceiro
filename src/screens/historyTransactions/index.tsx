import React from 'react';
import { View,Text, ScrollView } from "react-native";
import { Picker } from '@react-native-picker/picker';
import { styles } from "./style";
import { useTransactionHistory } from "@/src/hooks/historyTransaction/useHistoryTransaction";
  

export const HistoryTransactionsScreen = ()=>{

    const {
        mes, 
        setMes,
        ano, 
        setAno,
        meses,
        anos,
        transacoesFiltradas,
        receitas,
        despesas,
        saldo,
        carregando: loading,
  } = useTransactionHistory();


    return(

    <ScrollView
    contentContainerStyle={styles.scrollContainer}
    showsVerticalScrollIndicator={false}>

        <View style={styles.header}>
            
            <View style={styles.titleHeader}>
                <Text style={styles.titleText}>
                    Histórico de Transações
                </Text>
            </View>


            <View style={styles.periodContainer}>
                <Text style={styles.periodLabel}>Período:</Text>
                    
                <View style={styles.selectorsContainer}>
                    <View style={styles.pickerWrapperMes}>
                        <Picker
                             selectedValue={mes}
                            onValueChange={setMes}
                            style={styles.pickerMes}
                            dropdownIconColor="#FFFFFF"
                        >
                            {meses.map((monthName, index) => (
                            <Picker.Item 
                                key={index} 
                                label={monthName} 
                                value={index + 1} 
                            />
                            ))}
                        </Picker>
                    </View>
                
                    <View style={styles.pickerWrapperAno}>
                        <Picker
                            selectedValue={ano}
                            onValueChange={setAno}
                            style={styles.pickerAno}
                            dropdownIconColor="#FFFFFF"
                        >
                            {anos.map(anoItem => (
                                <Picker.Item 
                                    key={anoItem} 
                                    label={anoItem.toString()} 
                                    value={anoItem} 
                                />
                            ))}
                        </Picker>
                    </View>
                </View>
            </View>
        </View>
    </ScrollView>
  );
};