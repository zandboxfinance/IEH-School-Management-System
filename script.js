const video = document.getElementById('video')
var faceMatcher = null;

Promise.all([
  faceapi.nets.tinyFaceDetector.loadFromUri('/models'),
  faceapi.nets.faceLandmark68Net.loadFromUri('/models'),
  faceapi.nets.faceRecognitionNet.loadFromUri('/models'),
  faceapi.nets.faceExpressionNet.loadFromUri('/models'),
  faceapi.nets.ssdMobilenetv1.loadFromUri('/models')
]).then(start)

function startVideo() {
  navigator.getUserMedia(
    { video: {} },
    stream => video.srcObject = stream,
    err => console.error(err)
  )
}

async function start() {
  startVideo()
  console.log("Started")
  const labeledFaceDescriptors = await loadLabeledImages()
  faceMatcher = new faceapi.FaceMatcher(labeledFaceDescriptors, 0.6)
  console.log(labeledFaceDescriptors)
  console.log("Mid")
  console.log("END")
}

video.addEventListener('play', () => {
  const canvas = faceapi.createCanvasFromMedia(video)
  canvas.getContext("2d", {
    willReadFrequently: true,
  });
  document.body.append(canvas)
  const displaySize = { width: video.width, height: video.height }
  faceapi.matchDimensions(canvas, displaySize)
  setInterval(async () => {
    // FACE REC
    //faceapi.matchDimensions(canvas, displaySize)
    // --const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions()).withFaceLandmarks().withFaceExpressions()
    // const detections = await faceapi.detectAllFaces(video).withFaceLandmarks().withFaceDescriptors()
    let inputSize = 128;
    let scoreThreshold = 0.6;
    const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions(inputSize, scoreThreshold)).withFaceLandmarks().withFaceDescriptors()
    const resizedDetections = faceapi.resizeResults(detections, displaySize)
  
    const results = resizedDetections.map(d => {
      //console.log(d.descriptor)
      return faceMatcher.findBestMatch(d.descriptor)
    })
    canvas.getContext('2d', { willReadFrequently: true }).clearRect(0, 0, canvas.width, canvas.height)

    // canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height)
    // faceapi.draw.drawDetections(canvas, resizedDetections)

    faceapi.draw.drawFaceLandmarks(canvas, resizedDetections)
    // faceapi.draw.drawFaceExpressions(canvas, resizedDetections)

    results.forEach((result, i) => {
      const box = resizedDetections[i].detection.box
      const drawBox = new faceapi.draw.DrawBox(box, { label: result.toString() })
      const timestamp = new Date().toLocaleTimeString();
      drawBox.draw(canvas)
      drawTimestamp(canvas, box, timestamp, result);
    })
  
  }, 150)
})

function loadLabeledImages() {
  const labels = ['Black Widow', 'Captain America', 'Captain Marvel', 'Hawkeye', 'Jim Rhodes', 'Thor', 'Tony Stark']
  return Promise.all(
    labels.map(async label => {
      const descriptions = []
      for (let i = 1; i <= 2; i++) {
        const img = await faceapi.fetchImage(`./labeled_images/${label}/${i}.jpg`)
        const detections = await faceapi.detectSingleFace(img).withFaceLandmarks().withFaceDescriptor()
        descriptions.push(detections.descriptor)
      }

      return new faceapi.LabeledFaceDescriptors(label, descriptions)
    })
  )
}

function drawTimestamp(canvas, box, timestamp, result) {
  if(result._label != "unknown"){
    const ctx = canvas.getContext('2d');
    ctx.font = '12px Arial';
    ctx.fillStyle = 'red';
    ctx.fillText(timestamp, box.x, box.y - 20);
  }
} 
