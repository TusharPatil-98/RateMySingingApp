//const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

const admin = require('firebase-admin');
const functions = require('firebase-functions');

admin.initializeApp(functions.config().firebase);

var db = admin.firestore();

exports.createUser = functions.firestore
    .document('USER/{userID}')
    .onCreate((change, context) => {

	var count = 0;
	var userRef = db.collection('USER');
	var query = userRef.orderBy('createdDate','desc').get()
		.then(snapshot => {
		if (snapshot.empty) {
			console.log('No matching documents.');
		return;
		}  

		var batch = db.batch();
		snapshot.docs.forEach(doc => {
			if(count <= 9)
				count++;
			else{
				batch.delete(doc.ref);
				console.log('Deleted: ',doc.id, '=>', doc.data());
			}
		});
		return batch.commit().then(() => {
        return snapshot.size;
      });
		})
		.catch(err => {
			console.log('Error getting documents', err);
		});
});
	
	
