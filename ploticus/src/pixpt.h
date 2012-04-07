 
/* circles for direct pixel data point */
static int circliststart[] = { 0, 0, 0, 0, 10, 22, 36, 54, 54, 74, 74, 74 };
static int circpt[] = {

/* radius=3 */
/*         ***   */  
/*        *****  */  
/*       ******* */  
/*       ***o*** */  0, 3,  /* 0 */
/*       ******* */  1, 3,
/*        *****  */  2, 2,
/*         ***   */  3, 1,

		     0, 0,

/* radius=4 */
/*        *****   */  
/*       *******  */  
/*      ********* */  
/*      ********* */  
/*      ****o**** */  0, 4,   /* 5 x2 = 10 */
/*      ********* */  1, 4,
/*      ********* */  2, 4,
/*       *******  */  3, 3,
/*        *****   */  4, 2,

		      0, 0,

/* radius=5 */
/*        *****    */  
/*      *********  */  
/*      *********  */  
/*     *********** */  
/*     *********** */  
/*     *****o***** */  0, 5,   /* 11 x2 = 22 */
/*     *********** */  1, 5,
/*     *********** */  2, 5,
/*      *********  */  3, 4,
/*      *********  */  4, 4,
/*        *****    */  5, 2,

		       0, 0,

/* radius=6 */
/*          *        */  
/*       *******     */  
/*     ***********   */  
/*     ***********   */
/*    *************  */
/*    *************  */
/*    *************  */
/*   *******o******* */  0, 7,  /* 18 x2 = 36 */
/*    *************  */  1, 6,
/*    *************  */  2, 6,
/*    *************  */  3, 6,
/*     ***********   */  4, 5,
/*     ***********   */  5, 5,
/*       *******     */  6, 3,
/*          *        */  7, 0,

			 0, 0,

/* radius=7,8 */
/*        *****       */
/*      *********     */
/*     ***********    */
/*    *************   */
/*   ***************  */
/*   ***************  */
/*  ***************** */
/*  ***************** */
/*  ********o******** */ 0, 8,  /* 27 x2 = 54 */
/*  ***************** */ 1, 8,
/*  ***************** */ 2, 8,
/*   ***************  */ 3, 7,
/*   ***************  */ 4, 7,
/*    *************   */ 5, 6,
/*     ***********    */ 6, 5,
/*      *********     */ 7, 4,
/*        *****       */ 8, 2,

			0, 0,
/* radius=9 */
/*       *******       */  
/*     ***********     */
/*    *************    */
/*   ***************   */
/*  *****************  */
/*  *****************  */
/* ******************* */
/* ******************* */
/* ******************* */
/* *********o********* */  0, 9,  /* 37 x2 = 74 */
/* ******************* */  1, 9,
/* ******************* */  2, 9,
/* ******************* */  3, 9,
/*  *****************  */  4, 8,
/*  *****************  */  5, 8,
/*   ***************   */  6, 7,
/*    *************    */  7, 6,
/*     ***********     */  8, 5,
/*       *******       */  9, 3,

			   0, 0  };