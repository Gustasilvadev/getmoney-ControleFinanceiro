import { Tabs } from 'expo-router';
import {Footer} from '@/src/components/Footer';

export default function TabLayout() {
    return (
        <>
        <Tabs
            tabBar={() => <Footer />}
            screenOptions={{ headerShown: false }}
        >
            <Tabs.Screen name="home" />
            <Tabs.Screen name="HistoricoTransacoes" />
            <Tabs.Screen name="AdicionarTransacao" />
            <Tabs.Screen name="Graficos" />
            <Tabs.Screen name="Configuracao" />
            <Tabs.Screen name="Perfil" />
            <Tabs.Screen name="Metas" />
            <Tabs.Screen name="AdicionarMeta" />
        </Tabs>
        </>
    );
}