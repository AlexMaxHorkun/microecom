#!/bin/bash

/bin/bash -i delete_service.sh
/bin/bash -i microk8s_delete_image.sh
/bin/bash -i delete_image.sh
