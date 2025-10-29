import { useRouter } from 'expo-router';

export const useNavigation = () => {
  
  const router = useRouter();

  const navigateTo = {
    login: () => router.push('/auth/login'),
    register: () => router.push('/auth/register'),
    home:() => router.push('/Home'),
    back: () => router.back(),
  };

  return navigateTo;
};