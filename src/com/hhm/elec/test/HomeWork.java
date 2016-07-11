package com.hhm.elec.test;

public class HomeWork{

		public static void main(String []arg){
			int arr[] = {6,3,7,1,4,2};
			System.out.println(new HomeWork().logestSonArr(arr));
		}
		
		
		private int logestSonArr(int arr[]){
			
			int len = arr.length;
			int k = 1;
			
			int b[] = new int[len] ;
			b[1] = arr[0] ;
			for(int i = 1 ; i < len ;i++){
				if(b[k] <= arr[i]){
					b[++k] =  arr[i] ;
				}else{
					if(arr[i] < b[1]){
						b[1]= arr[i] ;
					}
					for(int j = 1;j!= k-1;){
						k=(k+j)/2 ;
						if(b[k]<arr[i]){
							j = k ;
						}else{
							b[k]= arr[i];
						}
					}
				}
			}
			return k;
		}
	}

